package br.com.juris.academico.controller;

import org.zkoss.xel.util.SimpleResolver;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.juris.academico.model.PessoaFisica;

@VariableResolver(SimpleResolver.class)
public class MySelectorComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = -5112406951674222617L;
	
	@Wire
	private Textbox textboxNome;
	
	@WireVariable("model")
	private PessoaFisica model;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		
		model = new PessoaFisica();
	}
	
	@Listen("onClick = #buttonSalvar")
	public void onClick$buttonSalvar(Event event){
		
		
		Messagebox.show(textboxNome.getValue() + "->" + model.getNome());
	}

	public PessoaFisica getModel() {
		return model;
	}

	public void setModel(PessoaFisica model) {
		this.model = model;
	}

}
