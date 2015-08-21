package br.com.juris.academico.controller;

import javax.ejb.EJBException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Textbox;

import br.com.juris.academico.arquitetura.AbstractComposer;
import br.com.juris.academico.model.PessoaFisica;
import br.com.juris.academico.model.Usuario;
import br.com.juris.academico.persistence.PessoaFisicaDao;

public class PessoaFisicaComposer extends AbstractComposer<PessoaFisica> {

	public PessoaFisicaComposer() {
		super(PessoaFisica.class);
	}

	private static final long serialVersionUID = -2468944244027967584L;
	
	// componentes do form
	private Textbox textboxCpf;
	private Textbox textboxNome;
	
	// componentes do list
	private Textbox filtroNome;
	private Textbox filtroCPF;
	private Decimalbox filtroTelefone;
	
	// TODO (icaromuniz) Implementar verificação para evitar que dê exceção quando o usuário digitar letras no campo CPF
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		
		super.doAfterCompose(comp);
		
		// desabilita o cpf na edição
		if (Executions.getCurrent().getParameter("ref") != null) {
			
			Object usuario = Executions.getCurrent().getSession().getAttribute("usuario");
			boolean isAdministrador = usuario != null && ((Usuario)usuario).isAdministrador();
			
			textboxNome.setDisabled(!isAdministrador);	// somente administrador altera nome
			textboxCpf.setDisabled(true);				// nunca altera CPF
		}
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

	@Override
	public void limpaFiltro() {
		filtroNome.setValue(null);
		filtroCPF.setRawValue(null);
		filtroTelefone.setRawValue(null);
	}

	@Override
	public void filtraLista() {
		this.setListaModelo(((PessoaFisicaDao)dao).findByFiltro(filtroNome.getValue(),
				filtroCPF.getValue() != null && !filtroCPF.getValue().isEmpty() ? filtroCPF.getValue() : null,
				filtroTelefone.getValue() != null ? filtroTelefone.getValue().longValue() : null));

		getBinder().notifyChange(this, "*");
	}
	
	@Override
	protected boolean isPersistenciaAutorizada(PessoaFisica modelo) {
		return true; // somente nome e cpf têm restrição de edição
	}

	@Override
	protected boolean isExclusaoAutorizada(PessoaFisica modelo) {
		return true; // restrições de integridade são suficientes
	}
}
