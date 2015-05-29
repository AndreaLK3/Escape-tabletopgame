package it.escape.server.view;

import it.escape.server.controller.GameMaster;
import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.model.game.Announcer;
import it.escape.strings.StringRes;
import it.escape.utils.FilesHelper;
import it.escape.utils.LogHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

public class Connection implements Observer, Runnable {
	
	protected static final Logger log = Logger.getLogger( Connection.class.getName() );
	
	private Socket clientSocket;
	
	private boolean running;
	
	private MessagingInterface messagingInterface;
	
	private ConnectionUnregisterInterface server;

	public Connection(Socket clientSocket, ConnectionUnregisterInterface server) {
		LogHelper.setDefaultOptions(log);
		this.clientSocket = clientSocket;
		this.running = true;
		this.server = server;
	}
	
	public void run() {
		try {
			sendWelcomeMessage();  // welcomes new player
			// setup required objects for a player to work properly
			messagingInterface = new SocketInterface(clientSocket);
			UserMessagesReporter.createUMR(messagingInterface);
			Announcer.getAnnouncerInstance().addObserver(this);
			GameMaster.newPlayerHasConnected(messagingInterface);
			
			// loop continuo: riempire la coda di ricezione
			while (running) {
				try {
					messagingInterface.receiveFromClient();
				} catch (NoSuchElementException e) {  // detect disconnection
					hasDisconnected();
				}
			}
			
			// natural end of the connection's life-cycle: close the socket
			try {
				clientSocket.close();
				log.info("Closed connection to " + clientSocket.getInetAddress().toString());
			} catch (IOException e) {
				log.severe("Cannot close the connection");
			}
			
			server.unregisterConnection(this);  // unregister from the connections list
		} catch (IOException e) {
			log.severe("Cannot establish connection");
		}
	}
	
	private void hasDisconnected() {
		messagingInterface.setConnectionDead();
		Announcer.getAnnouncerInstance().deleteObserver(this);
		GameMaster.playerHasDisconnected(messagingInterface);
		
		running = false;
		log.warning("Lost connection to " + clientSocket.getInetAddress().toString());
		// the connection thread will now terminate
	}
	
	private void sendWelcomeMessage() {
		PrintStream out;
		try {
			out = new PrintStream(clientSocket.getOutputStream());
			out.println(FilesHelper.streamToString(
					FilesHelper.getResourceFile("resources/MOTD.txt")));
		} catch (IOException e) {
			log.warning(StringRes.getString("view.connection.cantWelcome"));
		} 
	}

	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof Announcer) {
			Announcer a = (Announcer) arg0;
			PrintStream out;
			try {
				out = new PrintStream(clientSocket.getOutputStream());
				out.println(a.getMessage());
			} catch (IOException e) {
				log.warning(StringRes.getString("view.connection.cantAnnounce"));
			}
		}
	}
	
	public Socket getSocket() {
		return clientSocket;
	}

}
