package br.com.juris.academico.controller;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;

import br.com.juris.academico.geral.ComposerAbstrato;
import br.com.juris.academico.model.Usuario;
import br.com.juris.academico.persistence.UsuarioDao;
import br.com.juris.academico.service.Util;

public class UsuarioComposer extends ComposerAbstrato<Usuario> {

	private static final long serialVersionUID = 4353870365564922954L;

	public void onClick$buttonSalvar(Event event){
		UsuarioDao dao = Util.getDao(UsuarioDao.class);
		Clients.showNotification("Informações salvas com sucesso!");
	}

	public void onClick$buttonExcluir(Event event){
		Clients.showNotification("Exclusão efetuada com sucesso!");
	}
}
