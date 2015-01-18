package br.com.juris.academico.controller;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Textbox;

import br.com.juris.academico.geral.ComposerAbstrato;
import br.com.juris.academico.model.PessoaFisica;
import br.com.juris.academico.persistence.PessoaFisicaDao;

public class PessoaFisicaComposer extends ComposerAbstrato<PessoaFisica> {

	private static final long serialVersionUID = -2468944244027967584L;

	private Textbox textboxNome;
	
	public PessoaFisicaComposer(){
		this.setModel(new PessoaFisica());
		this.getModel().setUsuarioUltimaAlteracao("???");
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
	public void onClick$buttonSalvar(Event event){
		Clients.showNotification( "Nome: " + this.getModel().getNome());
		
		this.getModel().setNome(textboxNome.getValue());
		
		PessoaFisicaDao dao;
		try {
			dao = InitialContext.doLookup(PessoaFisicaDao.URI);
			dao.persist(this.getModel());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
