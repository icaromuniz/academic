package br.com.juris.academico.arquitetura;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import br.com.juris.academico.geral.EntidadeAbstrata;

public abstract class DaoAbstrato<T extends EntidadeAbstrata> {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	private Class<T> entityClass = null;

	public DaoAbstrato(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public T save(T entidade) {
		return em.merge(entidade);
	}

	public void remove(Integer idEntidade) {
		em.remove(em.find(entityClass, idEntidade));
	}

	public T find(Integer idEntidade) {
		return em.find(entityClass, idEntidade);
	}

	public EntityManager getEm() {
		return em;
	}
}
