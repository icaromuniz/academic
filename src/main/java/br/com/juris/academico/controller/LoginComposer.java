package br.com.juris.academico.controller;

import org.zkoss.bind.BindComposer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.Textbox;

import br.com.juris.academico.model.Usuario;
import br.com.juris.academico.persistence.UsuarioDao;
import br.com.juris.academico.service.Util;

public class LoginComposer extends BindComposer<Component> {

	private static final long serialVersionUID = -715245669746435907L;
	
	private Textbox textboxCpf;
	private Textbox textboxSenha;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		ConventionWires.wireFellows(getBinder().getView().getSpaceOwner(), this);
		getBinder().notifyChange(this, "*");
		if (textboxCpf != null) {
			textboxCpf.focus();
		}
	}

	public void executaLogin(){
		
		UsuarioDao usuarioDao = Util.getDao(UsuarioDao.class);
		Usuario usuario = null;
		
		if( textboxCpf.getValue() == null || textboxCpf.getValue().isEmpty() ){
			throw new WrongValueException(textboxCpf, "Informação obrigatória!");
		}
		
		if( textboxSenha.getValue() == null || textboxSenha.getValue().isEmpty() ){
			throw new WrongValueException(textboxSenha, "Informação obrigatória!");
		}
		
		// recupera o usuário cadastrado
		usuario = usuarioDao.findByAutenticacao(textboxCpf.getValue(), textboxSenha.getValue());
		
		if( usuario != null ){
			Executions.getCurrent().getSession().setAttribute("usuario", usuario);
			Executions.sendRedirect("/index.zul");
		} else {
			
			textboxCpf.setValue(null);
			textboxSenha.setValue(null);
			textboxCpf.focus();
			
			Clients.showNotification("Informações inválidas. Verifique os dados e tente novamente.", "error", null, null, 0);
		}
	}
	
	public void executaLogout(){
		Executions.getCurrent().getSession().invalidate();
		Executions.sendRedirect("/geral/login.zul");
	}
}
