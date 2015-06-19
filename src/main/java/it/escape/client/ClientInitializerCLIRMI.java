package it.escape.client;

import it.escape.client.connection.rmi.ClientRemoteInitializer;
import it.escape.client.connection.rmi.ProxyToServer;
import it.escape.client.controller.ClientChannelInterface;
import it.escape.client.controller.Relay;
import it.escape.client.controller.RelayForRMI;
import it.escape.client.controller.cli.UpdaterCLI;
import it.escape.client.view.cli.StateManagerCLI;
import it.escape.client.view.cli.Terminal;
import it.escape.strings.StringRes;

import java.io.PrintStream;
import java.util.Scanner;

public class ClientInitializerCLIRMI {
	
	private static ProxyToServer remoteServer;
	
	private static ClientLocalSettings locals;
	
	private static Relay relay;
	
	private static UpdaterCLI updater;
	
	private static StateManagerCLI stateManager;
	
	private static Scanner in = new Scanner(System.in);
	
	private static PrintStream out = System.out;
	
	private static Terminal view;
	
	public static void start(ClientLocalSettings Locals) {
		locals = Locals;
		enterServerAddress();
		out.println("Connecting...");
		
		stateManager = new StateManagerCLI();
		
		view = new Terminal(null, stateManager, in, out);
		
		// connect to server
		ClientRemoteInitializer.setCLIMode(stateManager, view);
		remoteServer = ClientRemoteInitializer.initializer(locals);
		
		relay = new RelayForRMI((ClientChannelInterface) remoteServer);
		remoteServer.bindDisconnCallback(view);
		view.bindRelay(relay);
		
		updater = new UpdaterCLI(stateManager, view);
		
		// register to server and start the actual communication
		ClientRemoteInitializer.postponedStart();
		
		view.mainLoop();
		
		// unexport the client; the program will stop shortly after
		ClientRemoteInitializer.stopRMI();
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
