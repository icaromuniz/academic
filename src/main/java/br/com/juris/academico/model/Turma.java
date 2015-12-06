package br.com.juris.academico.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.AssertTrue;
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
	@NotNull(message="dateboxTermino#Infomação obrigatória.")
	private Date dataTermino;
	
	@Column(length=50)
	private String sala;
	
	@NotNull(message="decimalboxValor#Informação obrigatória.")
	private BigDecimal valor;
	
	@OneToMany(mappedBy="turma")
	private List<Matricula> listaMatricula;
	
	@AssertTrue(message="dateboxTermino#O Término deve ser posterior ao Início da Turma.")
	private boolean isDataTerminoValida(){
		return this.dataInicio != null && this.dataTermino != null && dataTermino.after(dataInicio);
	}
	
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
	
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public List<Matricula> getListaMatricula() {
		return listaMatricula;
	}

	public void setListaMatricula(List<Matricula> listaMatricula) {
		this.listaMatricula = listaMatricula;
	}
	
	public Integer getQuantidadeMatriculasAtivas(){
		
		int counter = 0;
		
		for (Matricula matricula : this.getListaMatricula()) {
			if (matricula.isMatriculaAtiva()) {
				counter++;
			}
		}
		
		return counter;
	}

	@Override
	public String toString() {
		
		if (getNome() != null && getUnidade() != null) {
			return getNome() + " [" + getUnidade() + "]";
		}
		
		return super.toString();
	}
}
