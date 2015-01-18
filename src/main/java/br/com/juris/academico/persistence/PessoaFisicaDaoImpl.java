package br.com.juris.academico.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.juris.academico.model.PessoaFisica;

@Stateless
public class PessoaFisicaDaoImpl implements PessoaFisicaDao {

	@PersistenceContext
    private EntityManager em;


	public PessoaFisica findById(){
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void persist(PessoaFisica entidade) {
		em.persist(entidade);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remove(PessoaFisica entidade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PessoaFisica find(Integer idEntidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PessoaFisica> findByFiltro() {
		// TODO Auto-generated method stub
		return em.createQuery("select pf from PessoaFisica pf", PessoaFisica.class).getResultList();
	}
}
