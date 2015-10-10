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
@SequenceGenerator(name="seq_aula", sequenceName="seq_aula", allocationSize=1)
public class Aula extends EntidadeAbstrata {

	private static final long serialVersionUID = 4478284616286095139L;
	
	@Id
	@GeneratedValue(generator="seq_aula", strategy=GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(nullable=false)
	@NotNull(message="dateboxDataAula#Informação obrigatória.")
	private Date data;
	
	@ManyToOne
	@JoinColumn(name="id_disciplina", nullable=false)
	@NotNull(message="comboboxDisciplina#Informação obrigatória.")
	private Disciplina disciplina;
	
	@ManyToOne
	@JoinColumn(name="id_docente", nullable=false)
	@NotNull(message="comboboxDocente#Informação obrigatória.")
	private Docente docente;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}
}
