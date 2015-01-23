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
	public PessoaFisica save(PessoaFisica entidade) {
		return em.merge(entidade);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remove(PessoaFisica entidade) {
		PessoaFisica entidadeBD = em.find(PessoaFisica.class, entidade.getId());
		em.remove(entidadeBD);
	}

	@Override
	public PessoaFisica find(Integer idEntidade) {
		return em.find(PessoaFisica.class, idEntidade);
	}

	@Override
	public List<PessoaFisica> findByFiltro(String nome, Long cpf, Long telefone) {
		return em.createQuery("select pf from PessoaFisica pf", PessoaFisica.class).getResultList();
	}
}
