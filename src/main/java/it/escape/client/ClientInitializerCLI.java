package it.escape.client;

import it.escape.GlobalSettings;
import it.escape.client.controller.Relay;
import it.escape.client.controller.StateManager;
import it.escape.client.controller.Updater;
import it.escape.client.view.cli.Terminal;
import it.escape.strings.StringRes;

import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientInitializerCLI {
	
	private static ClientSocketInterface connection;
	
	private static StateManager stateManager;
	
	private static Relay relay;
	
	private static Updater updater;
	
	private static Terminal view;
	
	private static Scanner in = new Scanner(System.in);
	
	private static PrintStream out = System.out;
	
	public static void start() {
		enterServerAddress();
		try {
			connection = new ClientSocketInterface(GlobalSettings.getDestinationServerAddress());
			stateManager = new StateManager();
			relay = new Relay(connection);
			view = new Terminal(relay, stateManager, in, out);
			updater = new Updater(stateManager, view);
			connection.bindDisconnCallback(view);
			connection.addObserver(updater);
			new Thread(connection).start();
			view.mainLoop();
			
		} catch (UnknownHostException e) {
			out.println("Error: unknown host");
		} catch (IOException e) {
			out.println("Error: " + e.getMessage());
		}
	}
	
	private static void enterServerAddress() {
		out.println(String.format(
				StringRes.getString("client.text.enterServerAddress"),
				GlobalSettings.getDestinationServerAddress()));
		String input = in.nextLine();
		if (!"".equals(input)) {
			GlobalSettings.setDestinationServerAddress(input);
		}
	}
}
