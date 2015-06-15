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
import javax.validation.constraints.NotNull;

import br.com.juris.academico.geral.EntidadeAbstrata;

@Entity
@SequenceGenerator(name="seq_turma", sequenceName="seq_turma", allocationSize=1)
public class Turma extends EntidadeAbstrata {
	
	private static final long serialVersionUID = -807846605461060307L;
	
	@Id
	@GeneratedValue(generator="seq_turma", strategy=GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(nullable=false)
	@NotNull(message="textboxNome#Informação obrigatória.")
	private String nome;
	
	@Column(length=50, nullable=false)
	@NotNull(message="comboboxUnidade#Informação obrigatória.")
	private String unidade;

	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	@NotNull(message="dateboxInicio#Infomação obrigatória.")
	private Date dataInicio;
	
	@Temporal(TemporalType.DATE)
	private Date dataTermino;
	
	@Column(length=50)
	private String sala;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}
	
	@Override
	public String toString() {
		
		if (getNome() != null && getUnidade() != null) {
			return getNome() + " [" + getUnidade() + "]";
		}
		
		return super.toString();
	}
}
