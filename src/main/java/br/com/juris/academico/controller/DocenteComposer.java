package br.com.juris.academico.controller;

import java.util.List;

import javax.naming.NamingException;

import br.com.juris.academico.arquitetura.AbstractComposer;
import br.com.juris.academico.model.Docente;
import br.com.juris.academico.model.PessoaFisica;
import br.com.juris.academico.persistence.DocenteDao;
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
	
	// TODO (icaromuniz) Implementar exibição de mensagem pro usuário quando tentar salvar com pessoaFísica já utilizada

	@Override
	public void filtraLista() {
		this.setListaModelo(((DocenteDao)dao).findByFiltro(null, null));
		getBinder().notifyChange(this, "*");
	}

	public List<PessoaFisica> getListaPessoaFisica() throws NamingException{
		PessoaFisicaDao pessoaFisicaDao = Util.getDao(PessoaFisicaDao.class);
		return pessoaFisicaDao.findByFiltro(null, null, null);
	}
}
