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
			sqlQuery += " and m.pessoaFisica.nome like '%" + nomeAluno.toUpperCase() + "%' ";
		}
		
		if (nomeUnidade != null && !nomeUnidade.isEmpty()) {
			sqlQuery += " and m.turma.unidade like '" + nomeUnidade + "' ";
		}
		
		if (idTurma != null) {
			sqlQuery += " and m.turma.id = " + idTurma + " ";
		}
		
		if (formaPagamento != null && !formaPagamento.isEmpty()) {
			sqlQuery += " and m.formaPagamento = '" + formaPagamento + "' ";
		}
		
		sqlQuery += " order by m.pessoaFisica.nome, m.turma.nome";
				
		return getEm().createQuery(sqlQuery, Matricula.class).getResultList();
	}

	public boolean isAluno(Integer idPessoaFisica) {
		String sqlQuery = "select count(*) from Matricula m where m.pessoaFisica.id = :idPessoaFisica";
		return getEm().createQuery(sqlQuery).setParameter("idPessoaFisica", idPessoaFisica).getSingleResult() != null;
	}
}
