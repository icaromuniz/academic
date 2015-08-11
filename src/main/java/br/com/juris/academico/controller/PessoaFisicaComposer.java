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
import br.com.juris.academico.persistence.DocenteDao;
import br.com.juris.academico.persistence.MatriculaDao;
import br.com.juris.academico.persistence.PessoaFisicaDao;
import br.com.juris.academico.persistence.UsuarioDao;
import br.com.juris.academico.service.Util;

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
	
	// TODO (icaromuniz) Implementar verificação para evitar que dê exceção quando o usuário digitar letras no campo CPF
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		// desabilita o cpf na edição
		if (Executions.getCurrent().getParameter("ref") != null) {
			textboxCpf.setDisabled(true);
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
				filtroCPF.getValue() != null && !filtroCPF.getValue().isEmpty() ? Long.parseLong(filtroCPF.getValue()) : null,
				filtroTelefone.getValue() != null ? filtroTelefone.getValue().longValue() : null));

		getBinder().notifyChange(this, "*");
	}
	
	@Override
	protected boolean isPersistenciaAutorizada(PessoaFisica modelo) {
		
		Usuario usuario = (Usuario) Executions.getCurrent().getSession().getAttribute("usuario");
		
		return usuario.isAdministrador() || !isReferenciado( modelo );
	}

	private boolean isReferenciado(PessoaFisica modelo) {
		
		UsuarioDao usuarioDao = Util.getDao(UsuarioDao.class);
		DocenteDao docenteDao = Util.getDao(DocenteDao.class);
		MatriculaDao matriculaDao = Util.getDao(MatriculaDao.class);
		
		// verifica se a Pessoa Física é referenciada por outro Caso de Uso
		if (!usuarioDao.findByFiltro(null, modelo.getCpf(), null).isEmpty()) {	// Usuário
			return true;
		} else if (!docenteDao.findByFiltro(null, modelo.getCpf()).isEmpty()) {	// Docente
			return true;
		} else if (matriculaDao.isAluno(modelo.getId())) {						// Matrícula
			return true;
		}
		
		return false;
	}

	@Override
	protected boolean isExclusaoAutorizada(PessoaFisica modelo) {
		return true; // restrições de integridade são suficientes
	}
}
