package br.com.juris.academico.arquitetura;

import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;

import org.zkoss.bind.BindComposer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;

import br.com.juris.academico.geral.EntidadeAbstrata;

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
				modelo.setDataCriacao(new Date());
				modelo.setUsuarioCriacao("???");
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
		dao.save(modelo);
		Clients.showNotification( "Informações salvas com sucesso!" );
	}
	
	public void excluiRegistro(){
		dao.remove(modelo.getId());
		Clients.showNotification( "Exclusão efetuada com sucesso!" );
	}
	
	public abstract void limpaFiltro();

	public abstract void filtraLista();

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
