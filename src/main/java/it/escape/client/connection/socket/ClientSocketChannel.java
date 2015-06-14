package it.escape.client.connection.socket;

import it.escape.client.connection.BindDisconnectCallbackInterface;
import it.escape.client.connection.DisconnectedCallbackInterface;
import it.escape.client.controller.ClientSocketChannelInterface;
import it.escape.client.controller.MessageCarrier;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class ClientSocketChannel extends Observable implements Runnable, ClientSocketChannelInterface, BindDisconnectCallbackInterface {
	
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
	public void bindDisconnCallback(DisconnectedCallbackInterface disconnectCallback) {
		this.disconnectCallback = disconnectCallback;
	}
	
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
			System.out.println("Error: cannot close the connection");
		}
	}
	
	public void killConnection() {
		terminatedByClient = true;
		out.close();
	}
	
	public void sendMessage(String msg) {
		out.println(msg);
	}

	public void run() {
		mainLoop();		
	}
	
	   
}

