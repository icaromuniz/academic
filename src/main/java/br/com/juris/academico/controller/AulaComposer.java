package br.com.juris.academico.controller;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
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
	
	private TurmaDao turmaDao = Util.getDao(TurmaDao.class);
	private DisciplinaDao disciplinaDao = Util.getDao(DisciplinaDao.class);
	private DocenteDao docenteDao = Util.getDao(DocenteDao.class);
	
	// filtros
	private Datebox filtroData;
	private Combobox filtroTurma;
	private Combobox filtroDisciplina;
	private Textbox filtroDocente;
	
	public AulaComposer() {
		super(Aula.class);
	}

	@Override
	protected boolean isPersistenciaAutorizada(Aula modelo) {
		Object usuario = Executions.getCurrent().getSession().getAttribute("usuario");
		return usuario != null && ((Usuario)usuario).isAdministrador();
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
	
	public List<Turma> getListaTurma(){
		return turmaDao.findByFiltro(null, null, true);
	}
	
	public List<Disciplina> getListaDisciplina(){
		return disciplinaDao.findBySituacaoTurma(Executions.getCurrent().getDesktop().getRequestPath().equals("/aula/form.zul") ? true : null);
	}
	
	public List<Docente> getListaDocente(){
		return docenteDao.findByFiltro(null, null);
	}
}
