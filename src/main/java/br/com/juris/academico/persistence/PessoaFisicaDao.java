package br.com.juris.academico.persistence;

import java.util.List;

import javax.ejb.Stateless;

import br.com.juris.academico.arquitetura.DaoAbstrato;
import br.com.juris.academico.model.PessoaFisica;

@Stateless
public class PessoaFisicaDao extends DaoAbstrato<PessoaFisica>{

	public PessoaFisicaDao() {
		super(PessoaFisica.class);
	}

	public List<PessoaFisica> findByFiltro(String nome, Long cpf, Long telefone) {
		
		String qlString = "select pf from PessoaFisica pf where 1 = 1 ";
		
		if( nome != null && !nome.isEmpty() ){
			qlString = qlString.concat("and nome like '%" + nome.toUpperCase() + "%' ");
		}
		
		if( cpf != null ){
			qlString = qlString.concat("and cpf like '" + cpf + "%' ");
		}
		
		if( telefone != null ){
			qlString = qlString.concat("and (telefoneFixo like '" + telefone + 
					"%' or telefoneAlternativo like '" + telefone + "%') ");
		}
		
		return getEm().createQuery(qlString, PessoaFisica.class).getResultList();
	}
}
