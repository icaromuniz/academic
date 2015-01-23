package br.com.juris.academico.persistence;

import java.util.List;

import br.com.juris.academico.model.PessoaFisica;
import br.com.juris.academico.service.DAO;

public interface PessoaFisicaDao extends DAO<PessoaFisica> {

	public static final String URI = "java:app/academico/PessoaFisicaDaoImpl";

	public List<PessoaFisica> findByFiltro(String nome, Long cpf, Long telefone);
}
