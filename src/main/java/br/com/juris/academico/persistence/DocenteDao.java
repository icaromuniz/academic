package br.com.juris.academico.persistence;

import javax.ejb.Stateless;

import br.com.juris.academico.arquitetura.DaoAbstrato;
import br.com.juris.academico.model.Docente;

@Stateless
public class DocenteDao extends DaoAbstrato<Docente> {

	public DocenteDao() {
		super(Docente.class);
		// TODO Auto-generated constructor stub
	}

}
