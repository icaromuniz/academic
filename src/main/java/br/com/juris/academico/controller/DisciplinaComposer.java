package br.com.juris.academico.controller;

import java.util.List;

import br.com.juris.academico.arquitetura.AbstractComposer;
import br.com.juris.academico.model.Disciplina;
import br.com.juris.academico.model.Turma;
import br.com.juris.academico.persistence.TurmaDao;
import br.com.juris.academico.service.Util;

public class DisciplinaComposer extends AbstractComposer<Disciplina> {

	private static final long serialVersionUID = 2513924180250166084L;

	public DisciplinaComposer() {
		super(Disciplina.class);
	}
	
	@Override
	public void limpaFiltro() {
		// TODO Auto-generated method stub
	}

	@Override
	public void filtraLista() {
		// TODO Auto-generated method stub
	}
	
	public List<Turma> getListaTurma(){
		Boolean somenteAtiva = getModelo() != null && getModelo().getId() == null ? true : null;
		TurmaDao turmaDao = Util.getDao(TurmaDao.class);
		return turmaDao.findByDisponibilidade( somenteAtiva );
	}

	@Override
	protected boolean isPersistenciaAutorizada(Disciplina modelo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isExclusaoAutorizada(Disciplina modelo) {
		// TODO Auto-generated method stub
		return false;
	}
}
