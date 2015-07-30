package br.com.juris.academico.model;

import javax.persistence.Column;

import br.com.juris.academico.geral.EntidadeAbstrata;

public class Disciplina extends EntidadeAbstrata {

	private static final long serialVersionUID = -7153300919397783095L;
	
	private Integer id;
	
	private String nome;
	
	private Turma turma;
	
	private Docente docente;
	
	@Column(length=300)
	private String ementa;
	
	@Column(length=50)
	private String sala;

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
}
