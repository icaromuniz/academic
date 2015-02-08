package br.com.juris.academico.arquitetura;

import java.util.List;

import javax.naming.InitialContext;

import org.zkoss.bind.BindComposer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.ConventionWires;

import br.com.juris.academico.geral.EntidadeAbstrata;

@SuppressWarnings("serial")
public class AbstractComposer<T extends EntidadeAbstrata> extends BindComposer<Component> {

	private Class<T> classeModelo;

	private T modelo;

	private List<T> listaModelo;

	private DaoAbstrato<?> daoAbstrato;

	public AbstractComposer(Class<T> classeModelo) {
		this.classeModelo = classeModelo;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {

		super.doAfterCompose(comp);

		daoAbstrato = InitialContext.doLookup("java:module/" + classeModelo.getSimpleName() + "Dao");

		if (Executions.getCurrent().getDesktop().getRequestPath().endsWith("/form.zul")) { // abertura do form

			if (Executions.getCurrent().getParameter("ref") != null) { // carrega registro identificado

				modelo = (T) daoAbstrato.find(new Integer(Executions.getCurrent().getParameter("ref")));

			} else { // instancia novo objeto

				modelo = classeModelo.newInstance();
			}
		}

		ConventionWires.wireFellows(getBinder().getView().getSpaceOwner(), this);
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
