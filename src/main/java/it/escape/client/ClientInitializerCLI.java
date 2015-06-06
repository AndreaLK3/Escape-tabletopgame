package it.escape.client;

import it.escape.client.controller.Relay;
import it.escape.client.controller.StateManagerCLIInterface;
import it.escape.client.controller.UpdaterCLI;
import it.escape.client.controller.UpdaterCLItoTerminalInterface;
import it.escape.client.view.cli.StateManagerCLI;
import it.escape.client.view.cli.Terminal;
import it.escape.strings.StringRes;

import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientInitializerCLI {
	
	private static ClientSocketChannel connection;
	
	private static StateManagerCLI stateManager;
	
	private static Relay relay;
	
	private static UpdaterCLI updater;
	
	private static Terminal view;
	
	private static Scanner in = new Scanner(System.in);
	
	private static PrintStream out = System.out;
	
	private static ClientLocalSettings locals;
	
	public static void start(ClientLocalSettings Locals) {
		locals = Locals;
		enterServerAddress();
		try {
			connection = new ClientSocketChannel(
					locals.getDestinationServerAddress(),
					locals.getServerPort());
			stateManager = new StateManagerCLI();
			relay = new Relay(connection);
			view = new Terminal(relay, stateManager, in, out);
			updater = new UpdaterCLI((StateManagerCLIInterface)stateManager,
					(UpdaterCLItoTerminalInterface)view);
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
				locals.getDestinationServerAddress()));
		String input = in.nextLine();
		if (!"".equals(input)) {
			locals.setDestinationServerAddress(input);
		}
	}
}
