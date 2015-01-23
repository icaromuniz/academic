package br.com.juris.academico.model;

import java.util.Date;

import javax.persistence.Entity;

import br.com.juris.academico.geral.EntidadeAbstrata;

@Entity
public class PessoaFisica extends EntidadeAbstrata {

	private static final long serialVersionUID = 6155130143446479872L;
	
	private String nome;
	private Integer cpf; 
	private Date nascimento;
	private Integer telefoneFixo;
	private Integer telefoneCelular;
	private String email;
	private String endereço;
	private String bairro;

	public PessoaFisica() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCpf() {
		return cpf;
	}

	public void setCpf(Integer cpf) {
		this.cpf = cpf;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public Integer getTelefoneFixo() {
		return telefoneFixo;
	}

	public void setTelefoneFixo(Integer telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	public Integer getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(Integer telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereço() {
		return endereço;
	}

	public void setEndereço(String endereço) {
		this.endereço = endereço;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
}
