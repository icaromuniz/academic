package br.com.juris.academico.persistence;

import java.util.List;

import javax.ejb.Stateless;

import br.com.juris.academico.arquitetura.DaoAbstrato;
import br.com.juris.academico.model.Turma;

@Stateless
public class TurmaDao extends DaoAbstrato<Turma> {

	public TurmaDao() {
		super(Turma.class);
	}

	public List<Turma> findByFiltro (String nomeTurma, String unidade){
		
		String sqlQuery = "select t from Turma t where true is true ";
		
		if (nomeTurma != null && !nomeTurma.isEmpty()) {
			sqlQuery = sqlQuery.concat("and upper(t.nome) like '%" + nomeTurma.toUpperCase() + "%' ");
		}
		
		if (unidade != null && !unidade.isEmpty()) {
			sqlQuery = sqlQuery.concat("and t.unidade = '" + unidade + "' ");
		}
		
		sqlQuery = sqlQuery.concat("order by t.dataInicio");
		
		return getEm().createQuery(sqlQuery, Turma.class).getResultList();
	}
}
