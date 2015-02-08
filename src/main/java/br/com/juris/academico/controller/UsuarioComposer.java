package br.com.juris.academico.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;

import br.com.juris.academico.arquitetura.AbstractComposer;
import br.com.juris.academico.model.Usuario;
import br.com.juris.academico.persistence.UsuarioDao;
import br.com.juris.academico.service.Util;

public class UsuarioComposer extends AbstractComposer<Usuario>{

	public UsuarioComposer() {
		super(Usuario.class);
	}

	private static final long serialVersionUID = 4353870365564922954L;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		this.setModelo(new Usuario());
		this.getModelo().setSenha("teste");
		
		getBinder().notifyChange(this, "*");
	}

	public void onClick$buttonSalvar(Event event){
		
		UsuarioDao dao = Util.getDao(UsuarioDao.class);
		
		System.out.println(getModelo().getTipoUsuario());
		
		Clients.showNotification(getModelo().getLogin());
//		Clients.showNotification("Informações salvas com sucesso!");
	}

	public void onClick$buttonExcluir(Event event){
		Clients.showNotification("Exclusão efetuada com sucesso!");
	}
}
