package it.escape.server.view;

import it.escape.server.controller.GameMaster;
import it.escape.server.controller.UserMessagesReporter;
import it.escape.utils.LogHelper;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

public class Connection implements Observer, Runnable {
	
	protected static final Logger log = Logger.getLogger( Connection.class.getName() );
	
	private Socket clientSocket;
	
	private MessagingInterface messagingInterface;

	public Connection(Socket clientSocket) {
		LogHelper.setDefaultOptions(log);
		this.clientSocket = clientSocket;
	}
	
	public void run() {
		try {
			messagingInterface = new SocketInterface(clientSocket);
			UserMessagesReporter.createUMR(messagingInterface);
			GameMaster.newPlayerHasConnected(messagingInterface);
			// a questo punto abbiamo un nuovo player
			
			// ultima cosa da fare
			// loop continuo: riempire la coda di ricezione
			
		} catch (IOException e) {
			log.severe("Cannot establish connection");
		}
		
	}

	public void update(Observable arg0, Object arg1) {
		// leggi il messaggio da announcer
		// scrivi il messaggio sulla socket
	}

}
