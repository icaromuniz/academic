package br.com.juris.academico.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;

import br.com.juris.academico.arquitetura.AbstractComposer;
import br.com.juris.academico.model.Turma;
import br.com.juris.academico.model.Usuario;
import br.com.juris.academico.persistence.MatriculaDao;
import br.com.juris.academico.persistence.TurmaDao;
import br.com.juris.academico.service.Util;

public class TurmaComposer extends AbstractComposer<Turma> {

	private static final long serialVersionUID = -4900675433056035039L;
	
	// componentes do form
	private Grid grid;

	// componentes do list
	private Textbox filtroNome;
	private Combobox filtroUnidade;
	
	public TurmaComposer() {
		super(Turma.class);
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		
		super.doAfterCompose(comp);
		
		// desabilita edição da matrícula
		if (Executions.getCurrent().getDesktop().getRequestPath().contains("/form.zul")) {
			
			Usuario usuario = Executions.getCurrent().getSession().getAttribute("usuario") != null ?
					(Usuario) Executions.getCurrent().getSession().getAttribute("usuario") : null;
			
			atribuiPermissaoEdicao(grid, usuario != null && usuario.isAdministrador());
		}
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
	
	public Integer getQuantidadeMatriculada(){

		int quantidadeMatriculada = 0;
		
		if (this.getModelo() != null && this.getModelo().getId() != null) {
			MatriculaDao matriculaDao = Util.getDao(MatriculaDao.class);
			quantidadeMatriculada = matriculaDao.findByFiltro(null, null, getModelo().getId(), null, true).size();
		}
		
		return quantidadeMatriculada;
	}

	@Override
	protected boolean isPersistenciaAutorizada(Turma modelo) {
		Object usuario = Executions.getCurrent().getSession().getAttribute("usuario");
		return usuario != null && ((Usuario)usuario).isAdministrador();
	}

	@Override
	protected boolean isExclusaoAutorizada(Turma modelo) {
		Object usuario = Executions.getCurrent().getSession().getAttribute("usuario");
		return usuario != null && ((Usuario)usuario).isAdministrador();
	}
}
