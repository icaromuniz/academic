package br.com.juris.academico.persistence;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;

import br.com.juris.academico.arquitetura.DaoAbstrato;
import br.com.juris.academico.model.Matricula;

@Stateless
public class MatriculaDao extends DaoAbstrato<Matricula> {

	public MatriculaDao() {
		super(Matricula.class);
	}

	public List<Matricula> findByFiltro(String nomeAluno, String nomeUnidade, Integer idTurma, String formaPagamento, Boolean matriculaAtiva){
		
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
		
		if (matriculaAtiva != null) {
			sqlQuery += " and m.matriculaAtiva = " + matriculaAtiva;
		}
		
		sqlQuery += " order by m.pessoaFisica.nome, m.turma.nome, m.matriculaAtiva desc";
				
		return getEm().createQuery(sqlQuery, Matricula.class).getResultList();
	}

	/**
	 * Verifica se a Pessoa Física já está matriculada na Turma informada
	 * @param idPessoaFisica
	 * @param idTurma
	 * @return true se a Pessoa Física não está matriculada na Turma
	 */
	public boolean isMatriculaPermitida(Integer idPessoaFisica, Integer idTurma) {
		
		String sqlQuery = "select count(*) " +
				"from Matricula m " +
				"where m.matricula_ativa = true " +
				"	and m.id_pessoa_fisica = :idPessoaFisica " +
				"	and m.id_turma = :idTurma";
		
		BigInteger b = (BigInteger) getEm().createNativeQuery(sqlQuery)
				.setParameter("idPessoaFisica", idPessoaFisica)
				.setParameter("idTurma", idTurma)
				.getSingleResult();
		
		return b.intValue() == 0;
	}
}
