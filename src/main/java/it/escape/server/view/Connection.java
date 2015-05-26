package it.escape.server.view;

import it.escape.server.controller.GameMaster;
import it.escape.server.controller.UserMessagesReporter;
import it.escape.utils.FilesHelper;
import it.escape.utils.LogHelper;

import java.io.IOException;
import java.io.PrintStream;
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
			sendWelcomeMessage();
			messagingInterface = new SocketInterface(clientSocket);
			UserMessagesReporter.createUMR(messagingInterface);
			GameMaster.newPlayerHasConnected(messagingInterface);
			// a questo punto abbiamo un nuovo player
			
			// ultima cosa da fare
			// loop continuo: riempire la coda di ricezione
			while (true) {
				messagingInterface.receiveFromClient();
			}
			
		} catch (IOException e) {
			log.severe("Cannot establish connection");
		}
		
	}
	
	private void sendWelcomeMessage() {
		PrintStream out;
		try {
			out = new PrintStream(clientSocket.getOutputStream());
			out.println(FilesHelper.streamToString(
					FilesHelper.getResourceFile("resources/MOTD.txt")));
		} catch (IOException e) {
			log.warning("Couldn't send welcome message.");
		}
	}

	public void update(Observable arg0, Object arg1) {
		// leggi il messaggio da announcer
		// scrivi il messaggio sulla socket
	}

}
