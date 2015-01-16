package br.com.juris.academico.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public abstract class Util implements DAO {

	@PersistenceContext(unitName = "academico")
    private EntityManager em;

	public EntityManager getEm() {
		return em;
	}
}
