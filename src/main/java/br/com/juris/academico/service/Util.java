package br.com.juris.academico.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.juris.academico.geral.EntidadeAbstrata;

@Stateless
public class Util implements DAO {

	@PersistenceContext(unitName = "academico")
    private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@Override
	public void remove(EntidadeAbstrata entidade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntidadeAbstrata find(Integer idEntidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntidadeAbstrata save(EntidadeAbstrata entidade) {
		// TODO Auto-generated method stub
		return null;
	}
}
