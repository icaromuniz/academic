package br.com.juris.academico.controller;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.InputElement;

import br.com.juris.academico.arquitetura.AbstractComposer;
import br.com.juris.academico.model.Matricula;
import br.com.juris.academico.model.PessoaFisica;
import br.com.juris.academico.model.Turma;
import br.com.juris.academico.persistence.MatriculaDao;
import br.com.juris.academico.persistence.PessoaFisicaDao;
import br.com.juris.academico.persistence.TurmaDao;
import br.com.juris.academico.service.Util;

public class MatriculaComposer extends AbstractComposer<Matricula> {

	private static final long serialVersionUID = -8078166359065772931L;
	
	// componentes do form
	private Combobox comboboxPessoaFisica;
	
	// componentes do list
	private Textbox filtroNome;
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
		
		// TODO Verificar se o aluno já está matriculado
		
		super.salvaRegistro();
		
		atribuiPermissaoEdicao(comboboxPessoaFisica.getParent().getParent(), false);
	}

	@Override
	public void limpaFiltro() {
		filtroNome.setValue(null);
		filtroUnidade.setSelectedIndex(-1);
		((ListModelList<?>)filtroTurma.getModel()).clearSelection();
		filtroFormaPagamento.setSelectedIndex(-1);
	}

	@Override
	public void filtraLista() {
		setListaModelo(((MatriculaDao)dao).findByFiltro(filtroNome.getValue(), 
				filtroUnidade.getSelectedItem() != null ? filtroUnidade.getSelectedItem().getLabel() : null,
				filtroTurma.getSelectedItem() != null ? ((Turma)filtroTurma.getSelectedItem().getValue()).getId() : null,
				filtroFormaPagamento.getSelectedItem() != null ? filtroFormaPagamento.getSelectedItem().getLabel() : null));
		getBinder().notifyChange(this, "*");
	}
	
	public void atribuiPermissaoEdicao(Component component, boolean isEdicaoPermitida){
		
		if (component.getChildren() != null) {
			for (Component c : component.getChildren()) {
				atribuiPermissaoEdicao(c, isEdicaoPermitida);
			}
		}
		
		if(component instanceof InputElement && component.isVisible()){
			((InputElement) component).setDisabled(!isEdicaoPermitida);
		}
	}

	public List<PessoaFisica> getListaPessoaFisica(){
		PessoaFisicaDao pessoaFisicaDao = Util.getDao(PessoaFisicaDao.class);
		return pessoaFisicaDao.findByFiltro(null, null, null);
	}
	
	public List<Turma> getListaTurma(){
		TurmaDao turmaDao = Util.getDao(TurmaDao.class);
		return turmaDao.findByFiltro(null, null);
	}
}
