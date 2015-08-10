package br.com.juris.academico.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.juris.academico.geral.EntidadeAbstrata;

@Entity
public class Usuario extends EntidadeAbstrata {

	private static final long serialVersionUID = 6123911495403158914L;
	
	@Id
	@SequenceGenerator(name="SEQ_USUARIO", sequenceName="SEQ_USUARIO", allocationSize=1)
	@GeneratedValue(generator="SEQ_USUARIO", strategy=GenerationType.SEQUENCE)
	protected Integer id;

	@ManyToOne
	@JoinColumn(nullable=false)
	@NotNull(message="comboboxPessoaFisica#Informação obrigatória.")
	private PessoaFisica pessoaFisica;
	
	@Column(length=10)
	@NotEmpty(message="textboxSenha#Informação obrigatória.")
	@Length(message="textboxSenha#A senha deve ter dez caracteres.", min=10)
	private String senha;
	
	private boolean administrador = false;
	
	private boolean ativo = true;

	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isAdministrador() {
		return administrador;
	}
	
	public boolean getAdministrador(){
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
