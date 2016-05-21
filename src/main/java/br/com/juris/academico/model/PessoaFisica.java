package br.com.juris.academico.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

import br.com.juris.academico.geral.EntidadeAbstrata;
import br.com.juris.academico.persistence.PessoaFisicaDao;
import br.com.juris.academico.service.Util;

@Entity
public class PessoaFisica extends EntidadeAbstrata {

	private static final long serialVersionUID = 6155130143446479872L;
	
	@Id
	@SequenceGenerator(name="seq_pessoa_fisica", sequenceName="seq_pessoa_fisica", allocationSize=1)
	@GeneratedValue(generator="seq_pessoa_fisica", strategy=GenerationType.SEQUENCE)
	private Integer id;
	
	@NotEmpty(message="textboxNome#Informação obrigatória!")
	@Column(nullable=false)
	private String nome;

	@NotNull(message="textboxCpf#Informação obrigatória!")
	@Column(length=11, nullable=false, unique=true)
	@CPF(message="textboxCpf#Número de CPF inválido!")
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
	
	@Email(message = "textboxEmail#Endereço de e-mail inválido!")
	private String email;
	private String endereco;
	private String bairro;
	
	@AssertTrue(message = "textboxCpf#CPF já cadastrado no Sistema")
	public boolean isCpfValido(){
		
		if (this.getId() == null && !Util.getDao(PessoaFisicaDao.class).findByFiltro(null, cpf, null).isEmpty()) {
			return false;
		}
		
		return true;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome != null ? nome.trim().toUpperCase() : null;
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
		this.email = email.trim().toLowerCase();
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereço) {
		this.endereco = endereço != null ? endereço.toUpperCase() : null;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro != null ? bairro.toUpperCase() : null;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
