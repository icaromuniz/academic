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

	public List<PessoaFisica> findByFiltro(String nome, String cpf, Long telefone) {
		
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
		
		return getEm().createQuery(qlString.concat(" order by nome, cpf"), PessoaFisica.class).getResultList();
	}
	
	public List<PessoaFisica> findByFiltroContatos(String unidade, Integer idTurma){

		String qlString = "select distinct(pf) from PessoaFisica pf, Matricula m " +
				"where pf.id = m.pessoaFisica.id and m.matriculaAtiva = true ";
		
		if (unidade != null) {
			qlString += "and m.turma.unidade = '" + unidade + "'";
		}
		
		if (idTurma != null) {
			qlString += "and m.turma.id = " + idTurma;
		}
		
		return getEm().createQuery(qlString.concat(" order by pf.nome"), PessoaFisica.class).getResultList();
	}
}
