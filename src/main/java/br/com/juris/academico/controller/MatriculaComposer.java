package br.com.juris.academico.controller;

import java.util.List;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

import br.com.juris.academico.arquitetura.AbstractComposer;
import br.com.juris.academico.model.Matricula;
import br.com.juris.academico.model.PessoaFisica;
import br.com.juris.academico.model.Turma;
import br.com.juris.academico.persistence.MatriculaDao;
import br.com.juris.academico.persistence.PessoaFisicaDao;
import br.com.juris.academico.persistence.TurmaDao;
import br.com.juris.academico.service.Util;

public class MatriculaComposer extends AbstractComposer<Matricula> {

	private static final long serialVersionUID = -8078166359065772931L;
	
	// componentes do list
	private Textbox filtroNome;
	private Combobox filtroUnidade;
	private Combobox filtroTurma;
	private Combobox filtroFormaPagamento;

	public MatriculaComposer() {
		super(Matricula.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void limpaFiltro() {
		filtroNome.setValue(null);
		filtroUnidade.setSelectedIndex(-1);
		((ListModelList<?>)filtroTurma.getModel()).clearSelection();
		filtroFormaPagamento.setSelectedIndex(-1);
	}

	@Override
	public void filtraLista() {
		setListaModelo(((MatriculaDao)dao).findByFiltro(filtroNome.getValue(), 
				filtroUnidade.getSelectedItem() != null ? filtroUnidade.getSelectedItem().getLabel() : null,
				filtroTurma.getSelectedItem() != null ? ((Turma)filtroTurma.getSelectedItem().getValue()).getId() : null,
				filtroFormaPagamento.getSelectedItem() != null ? filtroFormaPagamento.getSelectedItem().getLabel() : null));
		getBinder().notifyChange(this, "*");
	}

	public List<PessoaFisica> getListaPessoaFisica(){
		PessoaFisicaDao pessoaFisicaDao = Util.getDao(PessoaFisicaDao.class);
		return pessoaFisicaDao.findByFiltro(null, null, null);
	}
	
	public List<Turma> getListaTurma(){
		TurmaDao turmaDao = Util.getDao(TurmaDao.class);
		return turmaDao.findByFiltro(null, null);
	}
}
