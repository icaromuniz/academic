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

	public List<Matricula> findByFiltro(String nomeAluno, String cpfAluno, String turma, String formaPagamento){
		String sqlQuery = "select m from Matricula m where true is true ";
		return getEm().createQuery(sqlQuery, Matricula.class).getResultList();
	}
}
