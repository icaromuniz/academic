package br.com.juris.academico.persistence;

import javax.ejb.Stateless;

import br.com.juris.academico.arquitetura.DaoAbstrato;
import br.com.juris.academico.model.Aula;

@Stateless
public class AulaDao extends DaoAbstrato<Aula> {

	public AulaDao() {
		super(Aula.class);
	}

}
