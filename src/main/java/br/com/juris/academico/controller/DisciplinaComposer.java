package br.com.juris.academico.controller;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

import br.com.juris.academico.arquitetura.AbstractComposer;
import br.com.juris.academico.model.Disciplina;
import br.com.juris.academico.model.Docente;
import br.com.juris.academico.model.Turma;
import br.com.juris.academico.model.Usuario;
import br.com.juris.academico.persistence.DisciplinaDao;
import br.com.juris.academico.persistence.DocenteDao;
import br.com.juris.academico.persistence.TurmaDao;
import br.com.juris.academico.service.Util;

public class DisciplinaComposer extends AbstractComposer<Disciplina> {

	private static final long serialVersionUID = 2513924180250166084L;
	
	// componentes do form
	Combobox comboboxTurma;
	Combobox comboboxDocente;
	
	// filtros do list
	Textbox filtroNome;
	Combobox filtroUnidade;
	Combobox filtroTurma;

	public DisciplinaComposer() {
		super(Disciplina.class);
	}
	
	@Override
	public void excluiRegistro() {
		super.excluiRegistro();
		
		if (getModelo().getId() == null) {
			comboboxTurma.setSelectedIndex(-1); 
			comboboxDocente.setSelectedIndex(-1);
		}
	}
	
	@Override
	public void limpaFiltro() {
		
		filtroNome.setValue(null);
		filtroUnidade.setSelectedIndex(-1);
		filtroTurma.setSelectedIndex(-1);
		((ListModelList<?>)filtroTurma.getModel()).clearSelection();
	}

	@Override
	public void filtraLista() {
		
		this.setListaModelo( ((DisciplinaDao)dao).findByFiltro(
				filtroNome.getValue(),
				filtroUnidade.getSelectedItem() != null ? filtroUnidade.getSelectedItem().getLabel() : null,
				filtroTurma.getSelectedItem()!= null ? ((Turma)filtroTurma.getSelectedItem().getValue()).getId() : null) );
		
		getBinder().notifyChange(this, "*");
	}
	
	@Override
	protected boolean isPersistenciaAutorizada(Disciplina modelo) {
		Usuario usuario = (Usuario) Executions.getCurrent().getSession().getAttribute("usuario");
		return usuario.isAdministrador();
	}

	@Override
	protected boolean isExclusaoAutorizada(Disciplina modelo) {
		Usuario usuario = (Usuario) Executions.getCurrent().getSession().getAttribute("usuario");
		return usuario.isAdministrador();
	}

	public List<Turma> getListaTurma(){
		Boolean somenteAtiva = getModelo() != null && getModelo().getId() == null ? true : null;
		TurmaDao turmaDao = Util.getDao(TurmaDao.class);
		return turmaDao.findByDisponibilidade( somenteAtiva );
	}

	public List<Docente> getListaDocente(){
		DocenteDao docenteDao = Util.getDao(DocenteDao.class); 
		return docenteDao.findByFiltro(null, null);
	}
}
