package it.escape.server.view;

import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class Connection implements Observer, Runnable {
	
	private Socket clientSocket;

	public Connection(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	public void run() {
		// crea messaging interface
		// registrala su UMR
		// gamemaste.newPlayerHasConnected(interface)
		// a questo punto abbiamo un nuovo player
		
		// ultima cosa da fare
		// loop continuo: riempire la coda di ricezione
		
	}

	public void update(Observable arg0, Object arg1) {
		// leggi il messaggio da announcer
		// scrivi il messaggio sulla socket
	}

}
