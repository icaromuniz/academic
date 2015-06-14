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

	public List<Docente> findByFiltro(String nomePessoaFisica, String numeroCpf){
		
		String sqlQuery = "select d from Docente d where true is true ";
		
		if (nomePessoaFisica != null && !nomePessoaFisica.isEmpty()) {
			sqlQuery = sqlQuery.concat("and d.pessoaFisica.nome like '%" + nomePessoaFisica.trim().toUpperCase() + "%' ");
		}
		
		if (numeroCpf != null && !numeroCpf.isEmpty()) {
			sqlQuery = sqlQuery.concat("and d.pessoaFisica.cpf like '" + numeroCpf.trim().toUpperCase() + "%' ");
		}
		
		return getEm().createQuery(sqlQuery, Docente.class).getResultList();
	}
}
