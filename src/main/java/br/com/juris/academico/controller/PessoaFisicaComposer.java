package br.com.juris.academico.controller;

import javax.ejb.EJBException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Textbox;

import br.com.juris.academico.arquitetura.AbstractComposer;
import br.com.juris.academico.model.PessoaFisica;
import br.com.juris.academico.persistence.PessoaFisicaDao;

public class PessoaFisicaComposer extends AbstractComposer<PessoaFisica> {

	public PessoaFisicaComposer() {
		super(PessoaFisica.class);
	}

	private static final long serialVersionUID = -2468944244027967584L;
	
	// componentes do form
	private Textbox textboxCpf;
	
	// componentes do list
	private Textbox filtroNome;
	private Textbox filtroCPF;
	private Decimalbox filtroTelefone;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		// desabilita o cpf na edição
		if (Executions.getCurrent().getParameter("ref") != null) {
//			decimalboxCpf.setDisabled(true);
		}
	}
	
	@Override
	public void limpaFiltro() {
		filtroNome.setValue(null);
		filtroCPF.setRawValue(null);
		filtroTelefone.setRawValue(null);
	}

	@Override
	public void filtraLista() {
		this.setListaModelo(((PessoaFisicaDao)dao).findByFiltro(filtroNome.getValue(),
				filtroCPF.getValue() != null && !filtroCPF.getValue().isEmpty() ? Long.parseLong(filtroCPF.getValue()) : null,
				filtroTelefone.getValue() != null ? filtroTelefone.getValue().longValue() : null));

		getBinder().notifyChange(this, "*");
	}
	
	@Override
	public void salvaRegistro() {
		
		try {
			super.salvaRegistro();
		} catch (EJBException e) {
			if (e.getCause().getCause().getCause().getCause().getLocalizedMessage().startsWith(
					"ERROR: duplicate key value violates unique constraint \"pessoafisica_cpf_key\"")) {
				throw new WrongValueException(textboxCpf, "O CPF informado já está cadastrado no sistema.");
			}
		}
	}
	
	@Override
	public void excluiRegistro() {
		
		super.excluiRegistro();
		
		if(getModelo().getId() == null){
			textboxCpf.setDisabled(false);
		}
	}
}
