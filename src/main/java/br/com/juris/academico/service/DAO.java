package br.com.juris.academico.service;

import br.com.juris.academico.geral.EntidadeAbstrata;

public interface DAO<T extends EntidadeAbstrata> {

	public void persist(T entidade);
	
	public void remove(T entidade);
	
	public T find(Integer idEntidade);
}
