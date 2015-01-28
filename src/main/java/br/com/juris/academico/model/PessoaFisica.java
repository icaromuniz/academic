package br.com.juris.academico.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.juris.academico.geral.EntidadeAbstrata;

@Entity
public class PessoaFisica extends EntidadeAbstrata {

	private static final long serialVersionUID = 6155130143446479872L;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(length=11, nullable=false)
	private String cpf;
	
	@Temporal(TemporalType.DATE)
	private Date nascimento;
	
	@Column(length=2)
	private String dddFixo;
	
	@Column(length=8)
	private String telefoneFixo;
	
	@Column(length=2)
	private String dddAlternativo;

	@Column(length=9)
	private String telefoneAlternativo;
	
	private String email;
	private String endereço;
	private String bairro;

	public PessoaFisica() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome != null ? nome.toUpperCase() : null;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefoneFixo() {
		return telefoneFixo;
	}

	public void setTelefoneFixo(String telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	public String getTelefoneAlternativo() {
		return telefoneAlternativo;
	}

	public void setTelefoneAlternativo(String telefoneAlternativo) {
		this.telefoneAlternativo = telefoneAlternativo;
	}

	public String getDddFixo() {
		return dddFixo;
	}

	public void setDddFixo(String dddFixo) {
		this.dddFixo = dddFixo;
	}

	public String getDddAlternativo() {
		return dddAlternativo;
	}

	public void setDddAlternativo(String dddAlternativo) {
		this.dddAlternativo = dddAlternativo;
	}
}
