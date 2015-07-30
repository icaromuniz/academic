package br.com.juris.academico.persistence;

import javax.ejb.Stateless;

import br.com.juris.academico.arquitetura.DaoAbstrato;
import br.com.juris.academico.model.Disciplina;

@Stateless
public class DisciplinaDao extends DaoAbstrato<Disciplina> {

	public DisciplinaDao() {
		super(Disciplina.class);
	}

}
