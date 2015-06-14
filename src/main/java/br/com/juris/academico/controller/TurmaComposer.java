package br.com.juris.academico.controller;

import br.com.juris.academico.arquitetura.AbstractComposer;
import br.com.juris.academico.model.Turma;
import br.com.juris.academico.persistence.TurmaDao;

public class TurmaComposer extends AbstractComposer<Turma> {

	private static final long serialVersionUID = -4900675433056035039L;

	public TurmaComposer() {
		super(Turma.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void limpaFiltro() {
		// TODO Auto-generated method stub

	}

	@Override
	public void filtraLista() {
		setListaModelo(((TurmaDao)dao).findByFiltro(null, null));
		getBinder().notifyChange(this, "*");
	}
}
