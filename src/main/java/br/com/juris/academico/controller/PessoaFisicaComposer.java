package br.com.juris.academico.controller;

import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.InputElement;

import br.com.juris.academico.geral.ComposerAbstrato;
import br.com.juris.academico.model.PessoaFisica;
import br.com.juris.academico.persistence.PessoaFisicaDao;

public class PessoaFisicaComposer extends ComposerAbstrato<PessoaFisica> {

	private static final long serialVersionUID = -2468944244027967584L;

	private Listbox listboxSelecao;
	AnnotateDataBinder binder = null;
	
	private Textbox filtroNome;
	private Decimalbox filtroCPF;
	private Decimalbox filtroTelefone;
	
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
		
		recuperaPrimeiroInput(comp).focus();
	}
	
	/** Percorre a árvore de componentes buscando pelo primeiro InputElement habilitado para retornar */
	private InputElement recuperaPrimeiroInput(Component component) {
		
		InputElement inputElement = null;
		
		if(component instanceof InputElement && !((InputElement) component).isDisabled()){
			return (InputElement) component;
		}
		
		if (component.getChildren() != null) {
			for (Component c : component.getChildren()) {
				
				inputElement = recuperaPrimeiroInput(c);
				
				if(inputElement != null){
					break;
				}
			}
		}
		
		return inputElement;
	}

	public void onClick$buttonSalvar(Event event) throws NamingException{
		
		PessoaFisicaDao pessoaFisicaDao = InitialContext.doLookup(PessoaFisicaDao.URI);
		
		Set<ConstraintViolation<PessoaFisica>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(this.getModel(), Default.class);
		
		if(constraintViolations != null && !constraintViolations.isEmpty()){
			throw new WrongValueException(self.getFellow("textboxNome"), constraintViolations.iterator().next().getMessage());
		}
		
		this.setModel(pessoaFisicaDao.save(getModel()));
		Clients.showNotification( "Informações salvas com sucesso!" );
	}
	
	public void onClick$buttonFiltrar(Event event){
		
		try {
			
			PessoaFisicaDao dao = InitialContext.doLookup(PessoaFisicaDao.URI);
			this.setModelList(dao.findByFiltro(filtroNome.getValue(),
					filtroCPF.getValue() != null ? filtroCPF.getValue().longValue() : null,
					filtroTelefone.getValue() != null ? filtroTelefone.getValue().longValue() : null));
			
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
