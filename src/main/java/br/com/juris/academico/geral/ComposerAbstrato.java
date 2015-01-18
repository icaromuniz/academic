package br.com.juris.academico.geral;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;

@SuppressWarnings("serial")
public abstract class ComposerAbstrato<T extends EntidadeAbstrata> extends GenericForwardComposer<Component>{

	/** Entidade corrente do composer */
	private T model;
	
	/** Lista de registros do caso de uso */
	private List<T> modelList;

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}

	public List<T> getModelList() {
		return modelList;
	}

	public void setModelList(List<T> modelList) {
		this.modelList = modelList;
	}
}
