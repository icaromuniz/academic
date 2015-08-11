package br.com.juris.academico.controller;

import java.util.List;

import javax.ejb.EJBTransactionRolledbackException;
import javax.naming.NamingException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

import br.com.juris.academico.arquitetura.AbstractComposer;
import br.com.juris.academico.model.Docente;
import br.com.juris.academico.model.PessoaFisica;
import br.com.juris.academico.persistence.DocenteDao;
import br.com.juris.academico.persistence.PessoaFisicaDao;
import br.com.juris.academico.service.Util;

public class DocenteComposer extends AbstractComposer<Docente> {

	private static final long serialVersionUID = 3273136131855629444L;
	
	// componentes do form
	private Combobox comboboxPessoaFisica;
	
	// componentes do list
	private Textbox filtroNome;
	private Textbox filtroCpf;

	public DocenteComposer() {
		super(Docente.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {

		super.doAfterCompose(comp);
		
		// desabilita a alteração da Pessoa Física
		if (getModelo() != null && getModelo().getId() != null) {
			comboboxPessoaFisica.setDisabled(true);
		}
	}

	@Override
	public void limpaFiltro() {
		filtroNome.setValue(null);
		filtroCpf.setValue(null);
	}
	
	@Override
	public void filtraLista() {
		this.setListaModelo(((DocenteDao)dao).findByFiltro(filtroNome.getValue(), filtroCpf.getValue()));
		getBinder().notifyChange(this, "*");
	}
	
	@Override
	public void salvaRegistro() {
		try {
			super.salvaRegistro();
		} catch (EJBTransactionRolledbackException e) {
			
			if (e.getCause().getCause().getMessage().contains("docente_id_pessoa_fisica_key")) {
				Clients.showNotification("Pessoa já cadastrada como Docente.", "error", null, null, 0);
			} else {
				e.printStackTrace();
			}
			
			return;
		}
		
		comboboxPessoaFisica.setDisabled(true);
	}
	
	@Override
	public void excluiRegistro() {
		super.excluiRegistro();
		comboboxPessoaFisica.setSelectedIndex(-1);
		comboboxPessoaFisica.setDisabled(false);
	}

	public List<PessoaFisica> getListaPessoaFisica() throws NamingException{
		PessoaFisicaDao pessoaFisicaDao = Util.getDao(PessoaFisicaDao.class);
		return pessoaFisicaDao.findByFiltro(null, null, null);
	}

	@Override
	protected boolean isPersistenciaAutorizada(Docente modelo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isExclusaoAutorizada(Docente modelo) {
		// TODO Auto-generated method stub
		return false;
	}
}
