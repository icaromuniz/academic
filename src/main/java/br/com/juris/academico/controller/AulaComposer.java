package br.com.juris.academico.controller;

import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import br.com.juris.academico.arquitetura.AbstractComposer;
import br.com.juris.academico.model.Aula;
import br.com.juris.academico.model.Disciplina;
import br.com.juris.academico.model.Docente;
import br.com.juris.academico.model.Turma;
import br.com.juris.academico.model.Usuario;
import br.com.juris.academico.persistence.AulaDao;
import br.com.juris.academico.persistence.DisciplinaDao;
import br.com.juris.academico.persistence.DocenteDao;
import br.com.juris.academico.persistence.TurmaDao;
import br.com.juris.academico.service.Util;

public class AulaComposer extends AbstractComposer<Aula> {

	private static final long serialVersionUID = 3273136131855629444L;
	
	private EventListener<Event> listenerExclusao = new EventListener<Event>() {

		@Override
		public void onEvent(Event event) throws Exception {
			if (event.getName().equals("onOK")) {
				cancelaAula(getModelo());
			}
		}
	};
	
	private TurmaDao turmaDao = Util.getDao(TurmaDao.class);
	private DisciplinaDao disciplinaDao = Util.getDao(DisciplinaDao.class);
	private DocenteDao docenteDao = Util.getDao(DocenteDao.class);
	
	// componentes do formulário
	private Combobox comboboxTurma;
	private Combobox comboboxDisciplina;
	private Combobox comboboxDocente;
	
	// filtros
	private Datebox filtroData;
	private Combobox filtroTurma;
	private Combobox filtroDisciplina;
	private Textbox filtroDocente;
	
	public AulaComposer() {
		super(Aula.class);
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		
		super.doAfterCompose(comp);
		
		// se for edição, popula a Turma correspondente à Disciplina da entidade
		if (Executions.getCurrent().getDesktop().getRequestPath().equals("/aula/form.zul") &&
				Executions.getCurrent().getDesktop().getQueryString() != null) {
			
			for (Object object : ((ListModelList<Object>)comboboxTurma.getModel())) {
				
				Turma turmaCombobox = (Turma) object;
				
				if (this.getModelo().getDisciplina().getTurma().equals(turmaCombobox)) {
					comboboxTurma.setValue(object.toString());
					break;
				}
			}
			
			atribuiPermissaoEdicao(comboboxTurma.getParent().getParent(), false);
		}
	}
	
	@Override
	public void salvaRegistro() {
		super.salvaRegistro();
		atribuiPermissaoEdicao(comboboxTurma.getParent().getParent(), this.getModelo().getId() == null && isExclusaoAutorizada(this.getModelo()));
	}
	
	@Override
	public void excluiRegistro() {
		Messagebox.show("Deseja cancelar a Aula permanentemente?", "Confirmação", 3,
				Messagebox.EXCLAMATION, listenerExclusao);
	}
	
	private void cancelaAula(Aula aula) {
		
		if (getModelo().getId() == null) {
			Clients.showNotification("Impossível excluir. Este registro não está salvo.", "warning", null, null, 0);
			return;
		}
		
		if (!getModelo().isAulaAtiva()) {
			Clients.showNotification("Matrícula já cancelada. Operação não permitida.", "warning", null, null, 0);
			return;
		}
		
		if (isExclusaoAutorizada(getModelo())) {
			
			aula.setAulaAtiva(false);
			aula.setUsuarioCancelamento((Usuario) Executions.getCurrent().getSession().getAttribute("usuario"));
			aula.setDataCancelamento(new Date());
			
			dao.save(getModelo());
			
		} else {
			Clients.showNotification("Usuário não autorizado a realizar esta operação.", "warning", null, null, 0);
		}
		
		getBinder().notifyChange(this, "*");
	}

	@Override
	protected boolean isPersistenciaAutorizada(Aula modelo) {
		Object usuario = Executions.getCurrent().getSession().getAttribute("usuario");
		return usuario != null && this.getModelo().getId() == null;
	}

	@Override
	protected boolean isExclusaoAutorizada(Aula modelo) {
		Object usuario = Executions.getCurrent().getSession().getAttribute("usuario");
		return usuario != null && ((Usuario)usuario).isAdministrador();
	}


	@Override
	public void limpaFiltro() {
		Executions.sendRedirect(Executions.getCurrent().getDesktop().getRequestPath());
	}

	@Override
	public void filtraLista() {
		this.setListaModelo(((AulaDao)dao).findByFiltro(filtroData.getValue(), 
				filtroDisciplina.getSelectedItem() != null ? ((Disciplina)filtroDisciplina.getSelectedItem().getValue()).getId() : null,
				filtroTurma.getSelectedItem() != null ? ((Turma)filtroTurma.getSelectedItem().getValue()).getId() : null,
				filtroDocente.getValue()));
		getBinder().notifyChange(this, "*");
	}
	
	public void trataSelecaoTurma(){
		this.getModelo().setDisciplina(null);
		comboboxDisciplina.setSelectedIndex(-1);
		getBinder().loadComponent(comboboxDisciplina, true);
		getBinder().notifyChange(this, "listaDisciplina");
	}
	
	public void trataSelecaoDisciplina(){
		for (Comboitem comboitem : comboboxDocente.getItems()) {
			if (comboitem.getValue().equals(( (Disciplina) comboboxDisciplina.getSelectedItem().getValue()).getDocente() )) {
				comboboxDocente.setSelectedItem(comboitem);
				this.getModelo().setDocente((Docente) comboitem.getValue());
				break;
			}
		}
	}
	
	public List<Turma> getListaTurma(){
		return turmaDao.findByFiltro(null, null, Executions.getCurrent().getDesktop().getRequestPath().equals("/aula/form.zul") &&
				Executions.getCurrent().getDesktop().getQueryString() == null ? true : null);
	}
	
	public List<Disciplina> getListaDisciplina(){
		
		// trecho para o formulário
		if (Executions.getCurrent().getDesktop().getRequestPath().equals("/aula/form.zul")) {
			
			if (comboboxTurma != null && comboboxTurma.getSelectedItem() != null) {
				return disciplinaDao.findByFiltro(null, null, ((Turma)comboboxTurma.getSelectedItem().getValue()).getId());
			} else if (Executions.getCurrent().getDesktop().getQueryString() != null) {
				return disciplinaDao.findByFiltro(null, null, null);
			} else {
				return null;
			}
		}
		
		return disciplinaDao.findByFiltro(null, null, null);
	}
	
	public List<Docente> getListaDocente(){
		return docenteDao.findByFiltro(null, null);
	}
}
