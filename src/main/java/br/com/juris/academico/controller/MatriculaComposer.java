package br.com.juris.academico.controller;

import java.util.Date;
import java.util.List;

import javax.ejb.EJBTransactionRolledbackException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import br.com.juris.academico.arquitetura.AbstractComposer;
import br.com.juris.academico.model.Matricula;
import br.com.juris.academico.model.PessoaFisica;
import br.com.juris.academico.model.Turma;
import br.com.juris.academico.model.Usuario;
import br.com.juris.academico.persistence.MatriculaDao;
import br.com.juris.academico.persistence.PessoaFisicaDao;
import br.com.juris.academico.persistence.TurmaDao;
import br.com.juris.academico.service.Util;

public class MatriculaComposer extends AbstractComposer<Matricula> {

	private static final long serialVersionUID = -8078166359065772931L;
	
	private EventListener<Event> listenerExclusao = new EventListener<Event>() {

		@Override
		public void onEvent(Event event) throws Exception {
			if (event.getName().equals("onOK")) {
				cancelaMatricula(getModelo());
			}
		}
	};
	
	// componentes do form
	private Combobox comboboxPessoaFisica;
	
	// componentes do list
	private Textbox filtroNome;
	private Combobox filtroSituacao;
	private Combobox filtroUnidade;
	private Combobox filtroTurma;
	private Combobox filtroFormaPagamento;

	public MatriculaComposer() {
		super(Matricula.class);
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		
		super.doAfterCompose(comp);
		
		// desabilita edição da matrícula
		if (Executions.getCurrent().getDesktop().getRequestPath().contains("/form.zul")) {
			atribuiPermissaoEdicao(comboboxPessoaFisica.getParent().getParent(), getModelo().getId() == null);
		}
	}
	
	@Override
	public void salvaRegistro() {
		
		if (!getModelo().isMatriculaAtiva()) {
			Clients.showNotification("Matrícula já cancelada. Operação não permitida.", "warning", null, null, 0);
			return;
		}
		
		try {
			
			super.salvaRegistro();
			
		} catch (EJBTransactionRolledbackException e) {
			
			// alerta se o aluno já estiver matriculado na turma selecionada
			if (e.getCausedByException().getCause().getMessage().contains("un_pessoa_turma")) {
				Messagebox.show("O Aluno informado já está matriculado \nna Turma selecionada.",
						"Matrícula duplicada!", 1, Messagebox.EXCLAMATION);
				return;
			}
			
			throw e;
		}
		
		atribuiPermissaoEdicao(comboboxPessoaFisica.getParent().getParent(), false);
	}
	
	@Override
	public void excluiRegistro() {
		Messagebox.show("Deseja cancelar a Matrícula permanentemente?", "Confirmação", 3,
				Messagebox.EXCLAMATION, listenerExclusao);
	}

	private void cancelaMatricula(Matricula matricula) {
		
		if (getModelo().getId() == null) {
			Clients.showNotification("Impossível excluir. Este registro não está salvo.", "warning", null, null, 0);
			return;
		}
		
		if (!getModelo().isMatriculaAtiva()) {
			Clients.showNotification("Matrícula já cancelada. Operação não permitida.", "warning", null, null, 0);
			return;
		}
		
		if (isExclusaoAutorizada(getModelo())) {
			
			matricula.setMatriculaAtiva(false);
			matricula.setUsuarioCancelamento((Usuario) Executions.getCurrent().getSession().getAttribute("usuario"));
			matricula.setDataCancelamento(new Date());
			
			super.salvaRegistro();
			
		} else {
			Clients.showNotification("Usuário não autorizado a realizar esta operação.", "warning", null, null, 0);
		}
	}

	@Override
	public void limpaFiltro() {
		filtroNome.setValue(null);
		filtroSituacao.setSelectedIndex(-1);
		filtroUnidade.setSelectedIndex(-1);
		((ListModelList<?>)filtroTurma.getModel()).clearSelection();
		filtroFormaPagamento.setSelectedIndex(-1);
	}

	@Override
	public void filtraLista() {
		
		Boolean filtroMatriculaAtiva = null;
		
		if (filtroSituacao.getSelectedItem() != null) {
			filtroMatriculaAtiva = "Ativa".equals(filtroSituacao.getSelectedItem().getLabel());
		}
		
		setListaModelo(((MatriculaDao)dao).findByFiltro(filtroNome.getValue(), 
				filtroUnidade.getSelectedItem() != null ? filtroUnidade.getSelectedItem().getLabel() : null,
				filtroTurma.getSelectedItem() != null ? ((Turma)filtroTurma.getSelectedItem().getValue()).getId() : null,
				filtroFormaPagamento.getSelectedItem() != null ? filtroFormaPagamento.getSelectedItem().getLabel() : null,
				filtroMatriculaAtiva));
		
		getBinder().notifyChange(this, "*");
	}
	
	public void emiteContrato(){
		
		try {
			Filedownload.save(
					JasperRunManager.runReportToPdf(Executions.getCurrent().getDesktop().getWebApp().getRealPath("/relatorio/contrato.jasper"),null),
					null, "Contrato.pdf");
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<PessoaFisica> getListaPessoaFisica(){
		PessoaFisicaDao pessoaFisicaDao = Util.getDao(PessoaFisicaDao.class);
		return pessoaFisicaDao.findByFiltro(null, null, null);
	}
	
	public List<Turma> getListaTurma(){
		Boolean somenteAtiva = getModelo() != null && getModelo().getId() == null ? true : null;
		TurmaDao turmaDao = Util.getDao(TurmaDao.class);
		return turmaDao.findByDisponibilidade( somenteAtiva );
	}
	
	public boolean isContratoDesabilitado(){
		
		// TODO (icaromuniz) Implementar verificação de 'Turma encerrada' e 'Matrícula Cancelada'
		
		return false;
	}

	@Override
	protected boolean isPersistenciaAutorizada(Matricula modelo) {
		return true;
	}

	@Override
	protected boolean isExclusaoAutorizada(Matricula modelo) {
		Object usuario = Executions.getCurrent().getSession().getAttribute("usuario");
		return usuario != null && ((Usuario)usuario).isAdministrador();
	}
}
