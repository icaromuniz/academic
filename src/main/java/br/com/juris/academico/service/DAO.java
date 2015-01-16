package br.com.juris.academico.service;

import javax.persistence.EntityManager;

import br.com.juris.academico.geral.EntidadeAbstrata;

public interface DAO<T extends EntidadeAbstrata> {
	
	public EntityManager getEm();

}
