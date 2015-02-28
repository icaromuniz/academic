package br.com.juris.academico.controller;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

import br.com.juris.academico.arquitetura.AbstractComposer;
import br.com.juris.academico.model.PessoaFisica;
import br.com.juris.academico.model.Usuario;
import br.com.juris.academico.persistence.PessoaFisicaDao;
import br.com.juris.academico.persistence.UsuarioDao;

public class UsuarioComposer extends AbstractComposer<Usuario>{
	
	private Combobox comboboxPf;
	
	// componentes do list
	private Textbox filtroCpf;

	public UsuarioComposer() {
		super(Usuario.class);
	}

	private static final long serialVersionUID = 4353870365564922954L;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		
		super.doAfterCompose(comp);

		// FIXME (icaromuniz) Retirar após implementar vinculado
		if (Executions.getCurrent().getDesktop().getRequestPath().endsWith("/form.zul")) {
			comboboxPf.setModel(new ListModelList<>(this.getListaPessoaFisica()));
		}
	}

	@Override
	public void limpaFiltro() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void filtraLista() {
		this.setListaModelo(((UsuarioDao)dao).findByFiltro(null, null));
		getBinder().notifyChange(this, "*");
	}

	public List<PessoaFisica> getListaPessoaFisica() throws NamingException{
		PessoaFisicaDao pessoaFisicaDao = InitialContext.doLookup(PessoaFisicaDao.URI);
		return pessoaFisicaDao.findByFiltro(null, null, null);
	}
}
