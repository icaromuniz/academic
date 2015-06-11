package br.com.juris.academico.persistence;

import java.util.List;

import javax.ejb.Stateless;

import br.com.juris.academico.arquitetura.DaoAbstrato;
import br.com.juris.academico.model.Docente;

@Stateless
public class DocenteDao extends DaoAbstrato<Docente> {

	public DocenteDao() {
		super(Docente.class);
		// TODO Auto-generated constructor stub
	}

	public List<Docente> findByFiltro(Integer idPessoaFisica, String cpf){
		// TODO Implementar filtro 
		return getEm().createQuery("select d from Docente d", Docente.class).getResultList();
	}
}
