package br.com.juris.academico.controller;

import java.util.List;

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

	public MatriculaComposer() {
		super(Matricula.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void limpaFiltro() {
		// TODO Auto-generated method stub

	}

	@Override
	public void filtraLista() {
		setListaModelo(((MatriculaDao)dao).findByFiltro(null, null, null, null));
		getBinder().notifyChange(this, "*");
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
