package br.com.juris.academico.controller;

import java.util.List;

import javax.naming.NamingException;

import br.com.juris.academico.arquitetura.AbstractComposer;
import br.com.juris.academico.model.Docente;
import br.com.juris.academico.model.PessoaFisica;
import br.com.juris.academico.persistence.PessoaFisicaDao;
import br.com.juris.academico.service.Util;

public class DocenteComposer extends AbstractComposer<Docente> {

	private static final long serialVersionUID = 3273136131855629444L;

	public DocenteComposer() {
		super(Docente.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void limpaFiltro() {
		// TODO Auto-generated method stub

	}

	@Override
	public void filtraLista() {
		// TODO Auto-generated method stub

	}

	public List<PessoaFisica> getListaPessoaFisica() throws NamingException{
		PessoaFisicaDao pessoaFisicaDao = Util.getDao(PessoaFisicaDao.class);
		return pessoaFisicaDao.findByFiltro(null, null, null);
	}
}
