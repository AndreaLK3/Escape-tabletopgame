package it.escape.client;

import it.escape.GlobalSettings;

import java.io.*;
import java.net.*;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class ClientSocketInterface extends Observable implements Runnable {
	
	private final static int PORTNUMBER = GlobalSettings.getServerPort();
	
	private final String SERVERIP;
	
	private Scanner in;
	
	private PrintStream out;
	
	private Socket clientSocket;
	
	private boolean running;
	
	private boolean terminatedByClient;  // connection terminated clientside vs killed by the server
	
	private MessageCarrier messaging;
	
	private DisconnectedCallbackInterface disconnectCallback;
	
	
	public ClientSocketInterface(String ipAddress) throws UnknownHostException, IOException {
		SERVERIP = ipAddress;
		clientSocket = new Socket(SERVERIP, PORTNUMBER);
		in = new Scanner(clientSocket.getInputStream());
		out = new PrintStream(clientSocket.getOutputStream());
		messaging = new MessageCarrier();
		running = true;
		terminatedByClient = false;
	}
	
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
				disconnectCallback.disconnected();
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

	@Override
	public void run() {
		mainLoop();		
	}
	
	   
}

