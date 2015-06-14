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

import br.com.juris.academico.geral.EntidadeAbstrata;

@Entity
public class Docente extends EntidadeAbstrata { 

	private static final long serialVersionUID = 3952165882848385526L;
	
	@Id
	@SequenceGenerator(name="SE_DOCENTE", sequenceName="SE_DOCENTE", allocationSize=1)
	@GeneratedValue(generator="SE_DOCENTE", strategy=GenerationType.SEQUENCE)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="ID_PESSOA_FISICA", nullable=false, unique=true)
	@NotNull(message="comboboxPessoaFisica#Informação obrigatória.")
	private PessoaFisica pessoaFisica;
	
	private String areaFormacao;
	
	private String nomeBanco;
	
	@Column(length=20)
	private String tipoConta;
	
	@Column(length=10)
	private String numeroAgencia;
	
	@Column(length=3)
	private String digitoAgencia;
	
	@Column(length=20)
	private String numeroConta;

	@Column(length=3)
	private String digitoConta;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public String getNomeBanco() {
		return nomeBanco;
	}

	public void setNomeBanco(String banco) {
		this.nomeBanco = banco;
	}

	public String getNumeroAgencia() {
		return numeroAgencia;
	}

	public void setNumeroAgencia(String agencia) {
		this.numeroAgencia = agencia;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numerocConta) {
		this.numeroConta = numerocConta;
	}

	public String getDigitoAgencia() {
		return digitoAgencia;
	}

	public void setDigitoAgencia(String digitoAgencia) {
		this.digitoAgencia = digitoAgencia;
	}

	public String getDigitoConta() {
		return digitoConta;
	}

	public void setDigitoConta(String digitoConta) {
		this.digitoConta = digitoConta;
	}

	public String getAreaFormacao() {
		return areaFormacao;
	}

	public void setAreaFormacao(String areaFormacao) {
		this.areaFormacao = areaFormacao;
	}
}
