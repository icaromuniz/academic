package br.com.juris.academico.controller;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

import br.com.juris.academico.arquitetura.AbstractComposer;
import br.com.juris.academico.model.Turma;
import br.com.juris.academico.persistence.TurmaDao;

public class TurmaComposer extends AbstractComposer<Turma> {

	private static final long serialVersionUID = -4900675433056035039L;

	// componentes do list
	private Textbox filtroNome;
	private Combobox filtroUnidade;
	
	public TurmaComposer() {
		super(Turma.class);
	}

	@Override
	public void limpaFiltro() {
		filtroNome.setValue(null);
		filtroUnidade.setSelectedIndex(-1);
	}

	@Override
	public void filtraLista() {
		setListaModelo(((TurmaDao)dao).findByFiltro(filtroNome.getValue(),
				filtroUnidade.getSelectedItem() != null ? filtroUnidade.getSelectedItem().getLabel() : null));
		getBinder().notifyChange(this, "*");
	}
}
