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
		return getEm().createQuery("select t from Turma t", Turma.class).getResultList();
	}
}
