package br.com.juris.academico.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import br.com.juris.academico.geral.EntidadeAbstrata;

@Entity
public class Docente extends EntidadeAbstrata {

	private static final long serialVersionUID = 3952165882848385526L;
	
	@Id
	@SequenceGenerator(name="SE_DOCENTE", sequenceName="SE_DOCENTE")
	@GeneratedValue(generator="SE_DOCENTE", strategy=GenerationType.SEQUENCE)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="ID_PESSOA_FISICA", nullable=false)
	private PessoaFisica pessoaFisica;
	
	private String banco;
	
	private String agencia;
	
	private String tipoConta;
	
	private String numeroConta;

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

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
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
}
