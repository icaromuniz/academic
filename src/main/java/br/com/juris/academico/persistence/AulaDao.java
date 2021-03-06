package br.com.juris.academico.persistence;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import br.com.juris.academico.arquitetura.DaoAbstrato;
import br.com.juris.academico.model.Aula;

@Stateless
public class AulaDao extends DaoAbstrato<Aula> {

	public AulaDao() {
		super(Aula.class);
	}

	public List<Aula> findByFiltro(Date data, Integer idDisciplina, Integer idTurma, String nomeDocente){
		
		String qlQuery = "select a from Aula a where true is true ";
		
		if (data != null) {
			qlQuery += " and to_char(a.data, 'dd/MM/yyyy') = '" + new SimpleDateFormat("dd/MM/yyyy").format(data) + "' ";
		}
		
		if (idDisciplina != null) {
			qlQuery += " and a.disciplina.id = " + idDisciplina;
		}
		
		if (idTurma != null) {
			qlQuery += " and a.disciplina.turma.id = " + idTurma;
		}
		
		if (nomeDocente != null && !nomeDocente.trim().isEmpty()) {
			qlQuery += " and upper(a.disciplina.docente.pessoaFisica.nome) like '%" + nomeDocente.trim().toUpperCase() + "%'";
		}
		
		return getEm().createQuery(qlQuery, Aula.class).getResultList();
	}
}
