package br.com.juris.academico.geral;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;

@SuppressWarnings("serial")
public abstract class ComposerAbstrato<T extends EntidadeAbstrata> extends GenericForwardComposer<Component>{

	/** Entidade corrente do composer */
	private T model;

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}
}
