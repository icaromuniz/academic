package br.com.juris.academico.persistence;

import java.util.List;

import javax.ejb.Stateless;

import br.com.juris.academico.arquitetura.DaoAbstrato;
import br.com.juris.academico.model.Matricula;

@Stateless
public class MatriculaDao extends DaoAbstrato<Matricula> {

	public MatriculaDao() {
		super(Matricula.class);
	}

	public List<Matricula> findByFiltro(String nomeAluno, String nomeUnidade, Integer idTurma, String formaPagamento){
		
		String sqlQuery = "select m from Matricula m where true is true ";
		
		if (nomeAluno != null && !nomeAluno.isEmpty()) {
			sqlQuery = sqlQuery.concat(" and m.pessoaFisica.nome like '%" + nomeAluno.toUpperCase() + "%' ");
		}
		
		if (nomeUnidade != null && !nomeUnidade.isEmpty()) {
			sqlQuery = sqlQuery.concat(" and m.turma.unidade like '" + nomeUnidade + "' ");
		}
		
		if (idTurma != null) {
			sqlQuery = sqlQuery.concat(" and m.turma.id = " + idTurma + " ");
		}
		
		if (formaPagamento != null && !formaPagamento.isEmpty()) {
			sqlQuery = sqlQuery.concat(" and m.formaPagamento = '" + formaPagamento + "' ");
		}
		
		return getEm().createQuery(sqlQuery, Matricula.class).getResultList();
	}
}
