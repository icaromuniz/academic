package br.com.juris.academico.controller;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindComposer;
import org.zkoss.zk.ui.Component;

import br.com.juris.academico.model.Aula;
import br.com.juris.academico.model.Turma;
import br.com.juris.academico.persistence.TurmaDao;
import br.com.juris.academico.service.Util;

public class IndexComposer extends BindComposer<Component> {

	private static final long serialVersionUID = -7000444445306178175L;

	public IndexComposer() {
		// TODO Auto-generated constructor stub
	}

	public List<Aula> getListaUltimasAulas(){
		return new ArrayList<>();
	}
	
	public List<Turma> getListaTurmasAbertas(){
		TurmaDao turmaDao = Util.getDao(TurmaDao.class);
		return turmaDao.findByDisponibilidade(true);
	}
}
