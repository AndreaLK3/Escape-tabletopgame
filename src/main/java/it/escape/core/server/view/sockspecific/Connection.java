package it.escape.core.server.view.sockspecific;

import it.escape.core.server.Master;
import it.escape.core.server.controller.UserMessagesReporter;
import it.escape.core.server.model.AnnouncerStrings;
import it.escape.core.server.model.SuperAnnouncer;
import it.escape.core.server.view.MessagingChannelStrings;
import it.escape.tools.strings.StringRes;
import it.escape.tools.utils.FilesHelper;
import it.escape.tools.utils.LogHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

public class Connection implements Observer, Runnable {
	
	private static final Logger LOGGER = Logger.getLogger( Connection.class.getName() );
	
	private Socket clientSocket;
	
	private boolean running;
	
	private MessagingChannelStrings messagingInterface;
	
	private ServerInterface server;
	
	private AnnouncerStrings announcer;

	public Connection(Socket clientSocket, ServerInterface server) {
		LogHelper.setDefaultOptions(LOGGER);
		this.clientSocket = clientSocket;
		this.running = true;
		this.server = server;
	}
	
	public void run() {
		try {
			sendWelcomeMessage();  // welcomes new player
			// setup required objects for a player to work properly
			messagingInterface = new SocketCommunication(clientSocket);
			UserMessagesReporter.createUMR(messagingInterface);
			Master.newPlayerHasConnected(messagingInterface, server.getLocals());
			SuperAnnouncer superAnnouncer = (SuperAnnouncer) UserMessagesReporter.getReporterInstance(messagingInterface).getAnnouncer();
			announcer = superAnnouncer.getSockAnnouncer();
			announcer.addObserver(this);
			
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
				//LOGGER.info("Closed connection to " + clientSocket.getInetAddress().toString());
			} catch (IOException e) {
				LOGGER.severe("Cannot close the connection");
			}
			
			server.unregisterConnection(this);  // unregister from the connections list
		} catch (IOException e) {
			LOGGER.severe("Cannot establish connection");
		}
	}
	
	private void hasDisconnected() {
		messagingInterface.setConnectionDead();
		announcer.deleteObserver(this);
		Master.playerHasDisconnected(messagingInterface);
		
		running = false;
		LOGGER.warning("Lost connection to " + clientSocket.getInetAddress().toString());
		// the connection thread will now terminate
	}
	
	private void sendWelcomeMessage() {
		PrintStream out;
		try {
			out = new PrintStream(clientSocket.getOutputStream());
			out.println(StringRes.getString("messaging.motd.start") + "\n" + 
					FilesHelper.streamToString(
							FilesHelper.getResourceFile("resources/MOTD.txt")
							) + "\n" +
					StringRes.getString("messaging.motd.end")
					);
		} catch (IOException e) {
			LOGGER.warning(StringRes.getString("view.connection.cantWelcome"));
		} 
	}

	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof AnnouncerStrings) {
			AnnouncerStrings a = (AnnouncerStrings) arg0;
			PrintStream out;
			try {
				out = new PrintStream(clientSocket.getOutputStream());
				out.println(a.getMessage());
			} catch (IOException e) {
				LOGGER.warning(StringRes.getString("view.connection.cantAnnounce"));
			}
		}
	}
	
	public Socket getSocket() {
		return clientSocket;
	}

}
