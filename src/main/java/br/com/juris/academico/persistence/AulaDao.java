package br.com.juris.academico.persistence;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import br.com.juris.academico.arquitetura.DaoAbstrato;
import br.com.juris.academico.model.Aula;

@Stateless
public class AulaDao extends DaoAbstrato<Aula> {

	public AulaDao() {
		super(Aula.class);
	}

	public List<Aula> findByFiltro(Date data, Integer idDisciplina, Integer idTurma, String nomeDocente){
		
		String qlQuery = "select a from Aula where true is true ";
		
		if (data != null) {
			qlQuery += " and data = " + data;
		}
		
		if (idDisciplina != null) {
			qlQuery += " and disciplina.id = " + idDisciplina;
		}
		
		if (idTurma != null) {
			qlQuery += " and disciplina.turma.id = " + idTurma;
		}
		
		if (nomeDocente != null && !nomeDocente.isEmpty()) {
			qlQuery += " and upper(disciplina.docente.pessoaFisica.nome) like '%" + nomeDocente.toUpperCase() + "%'";
		}
		
		return getEm().createQuery(qlQuery, Aula.class).getResultList();
	}
}
