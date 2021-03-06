package it.escape.core.client;

import it.escape.core.client.connection.rmi.ClientRemoteInitializer;
import it.escape.core.client.connection.rmi.ProxyToServer;
import it.escape.core.client.controller.ClientChannelInterface;
import it.escape.core.client.controller.Relay;
import it.escape.core.client.controller.RelayForRMI;
import it.escape.core.client.controller.cli.UpdaterCLI;
import it.escape.core.client.view.cli.StateManagerCLI;
import it.escape.core.client.view.cli.Terminal;
import it.escape.tools.GlobalSettings;
import it.escape.tools.strings.StringRes;

import java.io.PrintStream;
import java.util.Scanner;

public class ClientInitializerCLIRMI extends ClientInitializerCLI{
	
	private static ProxyToServer remoteServer;
	
	private static Relay relay;
	
	private static StateManagerCLI stateManager;
	
	private static Terminal view;
	
	/**The constructor (it doesn't really do anything meaningful, since we use only the static members of this class*/
	public ClientInitializerCLIRMI() {}
	
	public static void start(GlobalSettings localSettings) {
		locals = localSettings;
		enterServerAddress();
		
		stateManager = new StateManagerCLI();
		
		view = new Terminal(null, stateManager, in, out);
		
		// connect to server
		ClientRemoteInitializer.setCLIMode(stateManager, view);
		remoteServer = ClientRemoteInitializer.initializer(locals);
		
		relay = new RelayForRMI((ClientChannelInterface) remoteServer);
		remoteServer.bindDisconnCallback(view);
		view.bindRelay(relay);
		
		// register to server and start the actual communication
		ClientRemoteInitializer.postponedStart();
		
		view.mainLoop();
		
		// unexport the client; the program will stop shortly after
		ClientRemoteInitializer.stopRMI();
	}
	
}
