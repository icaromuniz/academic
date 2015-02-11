package br.com.juris.academico.arquitetura;

import java.util.List;

import javax.naming.InitialContext;

import org.zkoss.bind.BindComposer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;

import br.com.juris.academico.geral.EntidadeAbstrata;

@SuppressWarnings("serial")
public class AbstractComposer<T extends EntidadeAbstrata> extends BindComposer<Component> {

	private Class<T> classeModelo;

	private T modelo;

	private List<T> listaModelo;

	private DaoAbstrato<T> daoAbstrato;
	
	private Include includeListaBotoes;

	public AbstractComposer(Class<T> classeModelo) {
		this.classeModelo = classeModelo;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {

		super.doAfterCompose(comp);

		daoAbstrato = InitialContext.doLookup("java:module/" + classeModelo.getSimpleName() + "Dao");

		ConventionWires.wireFellows(getBinder().getView().getSpaceOwner(), this);

		if (Executions.getCurrent().getDesktop().getRequestPath().endsWith("/form.zul")) { // abertura do form

			if (Executions.getCurrent().getParameter("ref") != null) { // carrega registro identificado

				modelo = (T) daoAbstrato.find(new Integer(Executions.getCurrent().getParameter("ref")));

			} else { // instancia novo objeto

				modelo = classeModelo.newInstance();
			}
		}
		
		if (includeListaBotoes != null) {
			atualizaListaBotoes();
		}
	}

	private void atualizaListaBotoes() {

		String urlPath = Executions.getCurrent().getDesktop().getRequestPath();
		boolean isList = urlPath.endsWith("/list.zul");
		
		// atualiza referências
		((Button)includeListaBotoes.getFellow("buttonNovo")).setHref( urlPath.substring(0, urlPath.lastIndexOf("/")) + "/form.zul" );
		((Button)includeListaBotoes.getFellow("buttonBuscar")).setHref( urlPath.substring(0, urlPath.lastIndexOf("/")) + "/list.zul" );
		
		// atribui visibilidade adequada
		((Button)includeListaBotoes.getFellow("buttonSalvar")).setVisible(!isList);
		((Button)includeListaBotoes.getFellow("buttonExcluir")).setVisible(!isList);
		((Button)includeListaBotoes.getFellow("buttonBuscar")).setVisible(!isList);
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
