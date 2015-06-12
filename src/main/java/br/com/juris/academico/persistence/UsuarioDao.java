package br.com.juris.academico.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import com.sun.xml.internal.bind.v2.TODO;

import br.com.juris.academico.arquitetura.DaoAbstrato;
import br.com.juris.academico.model.Usuario;

@Stateless
public class UsuarioDao extends DaoAbstrato<Usuario> {

	UsuarioDao() {
		super(Usuario.class);
	}
	
	public List<Usuario> findByFiltro(String nomePessoaFisica, String tipoUsuario){
		
		String sqlQuery = "select u from Usuario u where 1=1 ";
		
		if (nomePessoaFisica != null && !nomePessoaFisica.isEmpty()) {
			sqlQuery = sqlQuery.concat("and u.pessoaFisica.nome like '" + nomePessoaFisica.trim().toUpperCase() + "%'");
		}
		
		// TODO Implementar filtro tipoUsuario
		
		return getEm().createQuery(sqlQuery, Usuario.class).getResultList();
	}

	public Usuario findByAutenticacao(String cpf, String senha) {
		try {
			return getEm().createQuery("select u from Usuario u where u.pessoaFisica.cpf = :cpf and u.senha = :senha", Usuario.class)
					.setParameter("cpf", cpf).setParameter("senha", senha).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
	