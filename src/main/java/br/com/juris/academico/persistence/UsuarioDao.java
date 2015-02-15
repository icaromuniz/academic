package br.com.juris.academico.persistence;

import java.util.List;

import javax.ejb.Stateless;

import br.com.juris.academico.arquitetura.DaoAbstrato;
import br.com.juris.academico.model.Usuario;

@Stateless
public class UsuarioDao extends DaoAbstrato<Usuario> {

	UsuarioDao() {
		super(Usuario.class);
	}
	
	public List<Usuario> findByFiltro(String nomePessoaFisica, String tipoUsuario){
		return getEm().createQuery("select u from Usuario u", Usuario.class).getResultList();
	}
}
	