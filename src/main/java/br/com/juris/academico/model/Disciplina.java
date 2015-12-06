package br.com.juris.academico.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.juris.academico.geral.EntidadeAbstrata;

@Entity
public class Disciplina extends EntidadeAbstrata {

	private static final long serialVersionUID = -7153300919397783095L;
	
	@Id
	@SequenceGenerator(name="seq_disciplina", sequenceName="seq_disciplina", allocationSize=1)
	@GeneratedValue(generator="seq_disciplina", strategy=GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(nullable=false, length=100)
	@NotEmpty(message="textboxNome#Informação obrigatória!")
	private String nome;
	
	@OneToOne
	@JoinColumn(name="id_turma", nullable=false)
	@NotNull(message="comboboxTurma#Informação obrigatória!")
	private Turma turma;
	
	@OneToOne
	@JoinColumn(name="id_docente", nullable=false)
	@NotNull(message="comboboxDocente#Informação obrigatória!")
	private Docente docente;
	
	private String ementa;
	
	@Column(length=50)
	private String sala;
	
	@Column(nullable=false)
	@NotNull(message="intboxCargaHoraria#Informação obrigatória!")
	private Integer cargaHoraria;
	
	@OneToMany(mappedBy="disciplina")
	private List<Aula> listaAula;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public String getEmenta() {
		return ementa;
	}

	public void setEmenta(String ementa) {
		this.ementa = ementa;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public Integer getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(Integer cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public List<Aula> getListaAula() {
		return listaAula;
	}

	public void setListaAula(List<Aula> listaAula) {
		this.listaAula = listaAula;
	}
	
	public Integer getQuantidadeAulasAtivas(){
		
		int count = 0;
		
		for (Aula aula : this.listaAula) {
			if (aula.isAulaAtiva()) {
				count++;
			}
		}
		
		return count;
	}
}
