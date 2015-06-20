package it.escape.core.client.connection.socket;

import it.escape.core.client.connection.BindDisconnectCallbackInterface;
import it.escape.core.client.connection.DisconnectedCallbackInterface;
import it.escape.core.client.controller.ClientChannelInterface;
import it.escape.core.client.controller.MessageCarrier;
import it.escape.core.client.controller.gui.UpdaterSwing;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClientSocketChannel extends Observable implements Runnable, ClientChannelInterface, BindDisconnectCallbackInterface {
	
	protected static final Logger LOGGER = Logger.getLogger( ClientSocketChannel.class.getName() );
	
	private Scanner in;
	
	private PrintStream out; 
	
	private Socket clientSocket;
	
	private boolean running;
	
	private boolean terminatedByClient;  // connection terminated clientside vs killed by the server
	
	private MessageCarrier messaging;
	
	private DisconnectedCallbackInterface disconnectCallback = null;
	
	
	public ClientSocketChannel(String ipAddress, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(ipAddress, port);
		in = new Scanner(clientSocket.getInputStream());
		out = new PrintStream(clientSocket.getOutputStream());
		messaging = new MessageCarrier();
		running = true;
		terminatedByClient = false;
	}
	
	/**
	 * Bind a DisconnectedCallbackInterface, it contains a single method
	 * to be called after an unexpected disconnection
	 * @param disconnectCallback
	 */
	@Override
	public void bindDisconnCallback(DisconnectedCallbackInterface disconnectCallback) {
		this.disconnectCallback = disconnectCallback;
	}
	
	/**If another object tries to observe this class, in practice it will
	 * observe controller.MessageCarrier, since this class passes the received messages to MessageCarrier*/
	@Override
	public synchronized void addObserver(Observer o) {
		messaging.addObserver(o);
	}

	public void mainLoop() {
		while (running) {
			try {
				String read = in.nextLine();
				messaging.newMessage(read);
			} catch (NoSuchElementException e) {  // detect disconnection
				LOGGER.fine("No connection to the server; I am going to close this socket...");					
				disconnected();
			}
		}
		
	}
	
	private void disconnected() {
		try {
			clientSocket.close();
			running = false;
			if (!terminatedByClient) {  // if the disconnection wasn't ordered by the client, do some cleanup
				if (disconnectCallback != null) {
					disconnectCallback.disconnected();
				}
			}
		} catch (IOException e) {
			LOGGER.warning("Error: cannot close the connection");
		}
	}
	
	@Override
	public void killConnection() {
		terminatedByClient = true;
		out.close();
	}
	
	@Override
	public void sendMessage(String msg) {
		out.println(msg);
	}

	public void run() {
		mainLoop();		
	}
	
	   
}

