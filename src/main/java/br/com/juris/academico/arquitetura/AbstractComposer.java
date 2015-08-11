package br.com.juris.academico.arquitetura;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJBTransactionRolledbackException;
import javax.naming.InitialContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.groups.Default;

import org.zkoss.bind.BindComposer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.WrongValuesException;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;

import br.com.juris.academico.geral.EntidadeAbstrata;
import br.com.juris.academico.model.Usuario;

@SuppressWarnings("serial")
public abstract class AbstractComposer<T extends EntidadeAbstrata> extends BindComposer<Component> {

	private Class<T> classeModelo;

	private T modelo;

	private List<T> listaModelo;

	protected DaoAbstrato<T> dao;
	
	private Include includeListaBotoes;

	public AbstractComposer(Class<T> classeModelo) {
		this.classeModelo = classeModelo;
	}
	
	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) throws Exception {
		
		// redireciona o usuário para a tela de login caso ele não esteja logado
		if( !Executions.getCurrent().getDesktop().getRequestPath().equals("/geral/login.zul") && 
				Executions.getCurrent().getSession().getAttribute("usuario") == null ){
			Executions.sendRedirect("/geral/login.zul");
		}
		
		return super.doBeforeCompose(page, parent, compInfo);
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		
		super.doAfterCompose(comp);

		// atualiza o título da página
		comp.getPage().setTitle("Júris Acadêmico - " + ((Window)comp).getTitle());

		// carrega instância do Dao do caso de uso
		dao = InitialContext.doLookup("java:module/" + classeModelo.getSimpleName() + "Dao");

		// executa os binds
		ConventionWires.wireFellows(getBinder().getView().getSpaceOwner(), this);

		// inicia o form
		if (Executions.getCurrent().getDesktop().getRequestPath().endsWith("/form.zul")) { // abertura do form

			if (Executions.getCurrent().getParameter("ref") != null) { // carrega registro identificado
				
				modelo = (T) dao.find(new Integer(Executions.getCurrent().getParameter("ref")));
				
			} else { // instancia novo objeto
				
				modelo = classeModelo.newInstance();
				modelo.setDataCriacao( new Date() );
				
				// FIXME (icaromuniz) Gamb para não dar exceção; Rever método de login
				if( Executions.getCurrent().getSession().getAttribute("usuario") != null ){
					modelo.setUsuarioCriacao( ((Usuario) Executions.getCurrent().getSession().getAttribute("usuario")).getPessoaFisica().getCpf() );
				}
			}
		}
		
		// atualiza o botões para o comportamento adequado à página
		if (includeListaBotoes != null) {
			atualizaListaBotoes();
		}
		
		// coloca o foco no primeiro Input da página
		recuperaPrimeiroInput(comp).focus();
		
		getBinder().notifyChange(this, "*");
	}
	
	public void salvaRegistro(){
		
		if (isPersistenciaAutorizada(modelo)) {
			
			Set<ConstraintViolation<T>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(this.getModelo(), Default.class);
			
			if(constraintViolations != null && !constraintViolations.isEmpty()){
				
				List<WrongValueException> listaExceções = new ArrayList<>();
				
				// exibe as violações
				for (ConstraintViolation<T> cv : constraintViolations) {
					listaExceções.add(new WrongValueException(getBinder().getView().getFellow(cv.getMessage().split("#")[0].trim()),
							cv.getMessage().split("#")[1]));
				}
				
				throw new WrongValuesException(listaExceções.toArray(new WrongValueException[0]));
			}
			
			modelo = dao.save(modelo);
			getBinder().notifyChange(this, "*");
			Clients.showNotification( "Informações salvas com sucesso!" );
			
		} else {
			Clients.showNotification("Usuário não autorizado a realizar esta operação.", "warning",
					null, null, 0);
		}
	}
	
	public void excluiRegistro(){
		
		if (getModelo().getId() == null) {
			Clients.showNotification("Impossível excluir. Este registro não está salvo.", "warning", null, null, 0);
			return;
		}
		
		// verifica se o usuário tem permissão
		if (isExclusaoAutorizada(modelo)) {
			
			// executa a exclusão
			try {
				dao.remove(modelo.getId());
			} catch (EJBTransactionRolledbackException e) {
				
				// informa que o registro está sendo referenciado
				if( e.getCause().getCause().getMessage().contains("violates foreign key constraint") ){
					Clients.showNotification("Este registro não pode ser excluído.", "error", null, null, 0);
					return;
				} else {
					e.printStackTrace();
				}
			}
			
			// cria um novo registro
			try {
				setModelo(classeModelo.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			
			getModelo().setDataCriacao(new Date());
			getModelo().setUsuarioCriacao( ((Usuario) Executions.getCurrent().getSession().getAttribute("usuario")).getPessoaFisica().getCpf() );
			
			getBinder().notifyChange(this, "*");
			Clients.showNotification( "Exclusão efetuada com sucesso!" );
			
		} else {
			Clients.showNotification("Usuário não autorizado a realizar esta operação.", "warning", null, null, 0);
		}
	}
	
	public abstract void limpaFiltro();

	public abstract void filtraLista();
	
	protected abstract boolean isPersistenciaAutorizada(T modelo);
	
	protected abstract boolean isExclusaoAutorizada(T modelo);

	protected void atribuiPermissaoEdicao(Component component, boolean isEdicaoPermitida){
		
		if (component.getChildren() != null) {
			for (Component c : component.getChildren()) {
				atribuiPermissaoEdicao(c, isEdicaoPermitida);
			}
		}
		
		if(component instanceof InputElement && component.isVisible() && !component.getId().equals("textboxObservacao")){
			((InputElement) component).setDisabled(!isEdicaoPermitida);
		}
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

	private void atualizaListaBotoes() {

		String urlPath = Executions.getCurrent().getDesktop().getRequestPath();
		boolean isList = urlPath.endsWith("/list.zul");
		
		// atualiza referências
		((Button)includeListaBotoes.getFellow("buttonNovo")).setHref( urlPath.substring(0, urlPath.lastIndexOf("/")) + "/form.zul" );
		((Button)includeListaBotoes.getFellow("buttonBuscar")).setHref( urlPath.substring(0, urlPath.lastIndexOf("/")) + "/list.zul" );
		
		// atribui visibilidade adequada (form)
		((Button)includeListaBotoes.getFellow("buttonSalvar")).setVisible(!isList);
		((Button)includeListaBotoes.getFellow("buttonExcluir")).setVisible(!isList);
		((Button)includeListaBotoes.getFellow("buttonBuscar")).setVisible(!isList);
		
		// atribui visibilidade adequada (list)
		((Button)includeListaBotoes.getFellow("buttonLimpar")).setVisible(isList);
		((Button)includeListaBotoes.getFellow("buttonFiltrar")).setVisible(isList);
	}
	
	public T getModelo() {
		return modelo;
	}
	
	public void setModelo(T modelo) {
		this.modelo = modelo;
	}

	public List<T> getListaModelo() {
		return listaModelo;
	}

	public void setListaModelo(List<T> listaModelo) {
		this.listaModelo = listaModelo;
	}

}
