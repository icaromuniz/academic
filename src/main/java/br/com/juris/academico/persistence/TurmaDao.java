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

	public List<Turma> findByFiltro (String nomeTurma, String unidade, Boolean isAberta){
		
		String sqlQuery = "select t from Turma t where true is true ";
		
		if (nomeTurma != null && !nomeTurma.isEmpty()) {
			sqlQuery += "and upper(t.nome) like '%" + nomeTurma.toUpperCase() + "%' ";
		}
		
		if (unidade != null && !unidade.isEmpty()) {
			sqlQuery += "and t.unidade = '" + unidade + "' ";
		}
		
		if (isAberta != null) {
			sqlQuery += "and t.dataTermino " + (isAberta ? ">=" : "<") + " now()";
		}
		
		sqlQuery = sqlQuery.concat("order by t.dataInicio desc, t.dataTermino desc, t.nome");
		
		return getEm().createQuery(sqlQuery, Turma.class).getResultList();
	}

	public List<Turma> findByDisponibilidade (Boolean isDisponivel){
		
		String sqlQuery = "select t from Turma t where true is true ";
		
		if (isDisponivel != null) {
			if (isDisponivel) {
				sqlQuery += "and now() <= t.dataTermino  ";	// não encerradas
			} else {
				sqlQuery += "and t.dataTermino < now() ";	// já encerradas
			}
		}
		
		sqlQuery += " order by t.unidade, t.nome";
		
		return getEm().createQuery(sqlQuery, Turma.class).getResultList();
	}
}
