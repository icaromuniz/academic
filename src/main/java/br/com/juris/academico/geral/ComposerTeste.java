package br.com.juris.academico.geral;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;

import br.com.juris.academico.service.DAO;

public class ComposerTeste extends ComposerAbstrato {

	private static final long serialVersionUID = 506215928028645842L;

	public void onClick$buttonSave(Event event){
		
		try {
			
			DAO dao = InitialContext.doLookup("java:app/academico/Util");
			
//			if( dao != null && dao.getEm() != null ){
//				Clients.showNotification("Dao carregadooo!");
//			}
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
