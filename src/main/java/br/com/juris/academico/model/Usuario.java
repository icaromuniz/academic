package br.com.juris.academico.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import br.com.juris.academico.dominiodiscreto.TipoUsuario;
import br.com.juris.academico.geral.EntidadeAbstrata;

@Entity
public class Usuario extends EntidadeAbstrata {

	private static final long serialVersionUID = 6123911495403158914L;

	@ManyToOne
	private PessoaFisica pessoaFisica;
	
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;
	
	@Column(unique=true)
	private String login;
	
	private String senha;

	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}
