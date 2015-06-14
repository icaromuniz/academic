package br.com.juris.academico.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.juris.academico.arquitetura.DaoAbstrato;
import br.com.juris.academico.model.Usuario;

@Stateless
public class UsuarioDao extends DaoAbstrato<Usuario> {

	UsuarioDao() {
		super(Usuario.class);
	}
	
	public List<Usuario> findByFiltro(String nomePessoaFisica, String cpf, String perfil){
		
		String sqlQuery = "select u from Usuario u where 1=1 ";
		
		if (nomePessoaFisica != null && !nomePessoaFisica.isEmpty()) {
			sqlQuery = sqlQuery.concat("and u.pessoaFisica.nome like '%" + nomePessoaFisica.trim().toUpperCase() + "%'");
		}
		
		if (cpf != null && !cpf.isEmpty()) {
			sqlQuery = sqlQuery.concat("and u.pessoaFisica.cpf like '" + cpf.trim() + "%'");
		}
		
		if (perfil != null) {
			
			if ("A".equals(perfil)) {
				sqlQuery = sqlQuery.concat("and u.administrador = true");
			} else if ("D".equals(perfil)) {
				sqlQuery = sqlQuery.concat("and u.administrador is null");
			} else if ("P".equals(perfil)) {
				sqlQuery = sqlQuery.concat("and u.administrador = false");
			}
		}
		
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
	