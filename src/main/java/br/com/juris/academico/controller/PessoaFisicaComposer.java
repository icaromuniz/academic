package br.com.juris.academico.controller;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import br.com.juris.academico.geral.ComposerAbstrato;
import br.com.juris.academico.model.PessoaFisica;
import br.com.juris.academico.persistence.PessoaFisicaDao;

public class PessoaFisicaComposer extends ComposerAbstrato<PessoaFisica> {

	private static final long serialVersionUID = -2468944244027967584L;

	private Listbox listboxSelecao;
	AnnotateDataBinder binder = null;
	
	private Textbox textboxNome;
	
	public PessoaFisicaComposer(){
		this.setModel(new PessoaFisica());
		this.getModel().setUsuarioUltimaAlteracao("???");
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		if( Executions.getCurrent().getParameter("ref") != null ){
			PessoaFisicaDao pessoaFisicaDao = InitialContext.doLookup(PessoaFisicaDao.URI);
			this.setModel(pessoaFisicaDao.find( new Integer(Executions.getCurrent().getParameter("ref"))));
		}
		
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}
	
	public void onClick$buttonSalvar(Event event) throws NamingException{
		PessoaFisicaDao pessoaFisicaDao = InitialContext.doLookup(PessoaFisicaDao.URI);
		setModel(pessoaFisicaDao.save(getModel()));
		Clients.showNotification( "Informações salvas com sucesso!" );
	}
	
	public void onClick$buttonFiltrar(Event event){
		
		try {
			
			PessoaFisicaDao dao = InitialContext.doLookup(PessoaFisicaDao.URI);
			this.setModelList(dao.findByFiltro(textboxNome.getValue(), null, null));
			binder.loadAll();
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public void onSelect$listboxSelecao(Event event){
		Executions.sendRedirect("/pessoafisica/form.zul?ref=" + 
				((PessoaFisica)listboxSelecao.getSelectedItem().getValue()).getId());
	}
	
	public void onClick$buttonExcluir(Event event){
		
		try {
			
			PessoaFisicaDao dao = InitialContext.doLookup(PessoaFisicaDao.URI);
			dao.remove(getModel());
			Clients.showNotification( "Exclusão efetuada com sucesso." );
			setModel(new PessoaFisica());
			binder.loadAll();
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
