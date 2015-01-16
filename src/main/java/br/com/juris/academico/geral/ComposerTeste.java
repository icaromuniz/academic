package br.com.juris.academico.geral;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;

public class ComposerTeste extends ComposerAbstrato {

	private static final long serialVersionUID = 506215928028645842L;

	public void onClick$buttonSave(Event event){
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@172.16.0.103:1521:tce", "SAE", "SAE");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Clients.showNotification("Teste");
	}
}
