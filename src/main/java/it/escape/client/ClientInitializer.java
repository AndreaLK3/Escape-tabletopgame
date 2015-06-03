package it.escape.client;

import it.escape.client.controller.Relay;
import it.escape.client.controller.StateManager;
import it.escape.client.controller.Updater;
import it.escape.client.view.Terminal;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientInitializer {
	
	private static ClientSocketInterface connection;
	
	private static StateManager stateManager;
	
	private static Relay relay;
	
	private static Updater updater;
	
	private static Terminal view;
	
	public static void main(String[] args) {
		String ipaddress = "127.0.0.1";
		try {
			connection = new ClientSocketInterface(ipaddress);
			stateManager = new StateManager();
			relay = new Relay(connection);
			view = new Terminal(relay, stateManager);
			updater = new Updater(stateManager, view);
			connection.addObserver(updater);
			new Thread(connection).start();
			view.mainLoop();
			
		} catch (UnknownHostException e) {
			System.out.println("Error: unknown host");
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
