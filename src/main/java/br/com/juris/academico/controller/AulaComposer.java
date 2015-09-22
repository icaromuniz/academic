package br.com.juris.academico.controller;

import org.zkoss.zk.ui.Executions;

import br.com.juris.academico.arquitetura.AbstractComposer;
import br.com.juris.academico.model.Aula;
import br.com.juris.academico.model.Usuario;

public class AulaComposer extends AbstractComposer<Aula> {

	private static final long serialVersionUID = 3273136131855629444L;
	
	public AulaComposer() {
		super(Aula.class);
	}
	

	@Override
	protected boolean isPersistenciaAutorizada(Aula modelo) {
		Object usuario = Executions.getCurrent().getSession().getAttribute("usuario");
		return usuario != null && ((Usuario)usuario).isAdministrador();
	}

	@Override
	protected boolean isExclusaoAutorizada(Aula modelo) {
		Object usuario = Executions.getCurrent().getSession().getAttribute("usuario");
		return usuario != null && ((Usuario)usuario).isAdministrador();
	}


	@Override
	public void limpaFiltro() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void filtraLista() {
		// TODO Auto-generated method stub
		
	}
}
