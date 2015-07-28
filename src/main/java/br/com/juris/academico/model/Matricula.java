package br.com.juris.academico.model;

import java.util.Date;

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
public class Matricula extends EntidadeAbstrata {
	
	private static final long serialVersionUID = -7328122811216811972L;
	
	@Id
	@SequenceGenerator(name="seq_matricula", sequenceName="seq_matricula", allocationSize=1)
	@GeneratedValue(generator="seq_matricula", strategy=GenerationType.SEQUENCE)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="id_pessoa_fisica", nullable=false)
	@NotNull(message="comboboxPessoaFisica#Informação obrigatória.")
	private PessoaFisica pessoaFisica;
	
	@ManyToOne
	@JoinColumn(name="id_turma", nullable=false)
	@NotNull(message="comboboxTurma#Informação obrigatória.")
	private Turma turma;
	
	@Column(nullable=false, length=100)
	@NotNull(message="comboboxFormaPagamento#Informação obrigatória.")
	private String formaPagamento;
	
	@Column(nullable=false, length=100)
	@NotNull(message="comboboxComoConheceu#Informação obrigatória.")
	private String comoConheceu;
	
	private boolean modulo1 = false;
	private boolean modulo2 = false;
	private boolean modulo3 = false;
	private String observacao;
	
	@ManyToOne
	@JoinColumn(name="id_usuario_cancelamento")
	private Usuario usuarioCancelamento;
	private Date dataCancelamento;
	
	@Column(name="matricula_ativa")
	private Boolean matriculaAtiva;

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

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public String getComoConheceu() {
		return comoConheceu;
	}

	public void setComoConheceu(String comoConheceu) {
		this.comoConheceu = comoConheceu;
	}

	public boolean isModulo1() {
		return modulo1;
	}

	public void setModulo1(boolean modulo1) {
		this.modulo1 = modulo1;
	}

	public boolean isModulo2() {
		return modulo2;
	}

	public void setModulo2(boolean modulo2) {
		this.modulo2 = modulo2;
	}

	public boolean isModulo3() {
		return modulo3;
	}

	public void setModulo3(boolean modulo3) {
		this.modulo3 = modulo3;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Usuario getUsuarioCancelamento() {
		return usuarioCancelamento;
	}

	public void setUsuarioCancelamento(Usuario usuarioCancelamento) {
		this.usuarioCancelamento = usuarioCancelamento;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public Boolean isMatriculaAtiva() {
		return matriculaAtiva;
	}

	public void setMatriculaAtiva(Boolean matriculaAtiva) {
		this.matriculaAtiva = matriculaAtiva;
	}

}
