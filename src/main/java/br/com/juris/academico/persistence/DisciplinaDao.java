package br.com.juris.academico.persistence;

import java.util.List;

import javax.ejb.Stateless;

import br.com.juris.academico.arquitetura.DaoAbstrato;
import br.com.juris.academico.model.Disciplina;

@Stateless
public class DisciplinaDao extends DaoAbstrato<Disciplina> {

	public DisciplinaDao() {
		super(Disciplina.class);
	}

	public List<Disciplina> findByFiltro(String nome, String unidade, Integer idTurma){
		String sqlQuery = "select d from Disciplina d where true = true ";
		
		if (nome != null && !nome.isEmpty()) {
			sqlQuery += "and upper(nome) like '%" + nome.trim().toUpperCase() + "%' ";
		}
		
		if (unidade != null) {
			sqlQuery += "and turma.unidade = '" + unidade + "' ";
		}
		
		if (idTurma != null) {
			sqlQuery += "and turma.id = " + idTurma;
		}
		
		return getEm().createQuery(sqlQuery, Disciplina.class).getResultList();
	}
}
