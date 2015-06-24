package it.escape.core.client;

import it.escape.core.client.connection.socket.ClientSocketChannel;
import it.escape.core.client.controller.Relay;
import it.escape.core.client.controller.RelayForSocket;
import it.escape.core.client.controller.cli.StateManagerCLIInterface;
import it.escape.core.client.controller.cli.UpdaterCLI;
import it.escape.core.client.controller.cli.UpdaterCLItoTerminalInterface;
import it.escape.core.client.view.cli.StateManagerCLI;
import it.escape.core.client.view.cli.Terminal;
import it.escape.tools.GlobalSettings;
import it.escape.tools.strings.StringRes;

import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientInitializerCLISocket extends ClientInitializerCLI {
	
	private static ClientSocketChannel connection;
	
	private static StateManagerCLI stateManager;
	
	private static Relay relay;
	
	private static UpdaterCLI updater;
	
	private static Terminal view;
	
	/**The constructor (it doesn't really do anything, since we use only the static members of this class*/
	public ClientInitializerCLISocket() {}
	
	public static void start(GlobalSettings localSettings) {
		locals = localSettings;
		enterServerAddress();
		try {
			connection = new ClientSocketChannel(
					locals.getDestinationServerAddress(),
					locals.getServerPort());
			stateManager = new StateManagerCLI();
			relay = new RelayForSocket(connection);
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
}
