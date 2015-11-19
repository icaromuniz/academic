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
		
		String qlQuery = "select a from Aula a where true is true ";
		
		if (data != null) {
			qlQuery += " and a.data = " + data;
		}
		
		if (idDisciplina != null) {
			qlQuery += " and a.disciplina.id = " + idDisciplina;
		}
		
		if (idTurma != null) {
			qlQuery += " and a.disciplina.turma.id = " + idTurma;
		}
		
		if (nomeDocente != null && !nomeDocente.isEmpty()) {
			qlQuery += " and upper(a.disciplina.docente.pessoaFisica.nome) like '%" + nomeDocente.toUpperCase() + "%'";
		}
		
		return getEm().createQuery(qlQuery, Aula.class).getResultList();
	}
}
