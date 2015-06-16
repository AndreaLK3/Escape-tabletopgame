package it.escape.client.connection.rmi;

import it.escape.client.ClientLocalSettings;
import it.escape.client.controller.cli.StateManagerCLIInterface;
import it.escape.client.controller.gui.ClientProceduresInterface;
import it.escape.client.view.cli.Terminal;
import it.escape.server.SockServerInitializer;
import it.escape.server.view.rmispecific.ServerRemoteInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

/**This static class initializes one of the 2 kinds of ClientRemote, 
 * which can use the Swing View or the Terminal View */
public class ClientRemoteInitializer {
	
	private static final Logger LOG = Logger.getLogger( ClientRemoteInitializer.class.getName() );
	
	private static String ipAddress;
	
	private static Terminal terminal = null;
	
	private static StateManagerCLIInterface stateManager = null;
	
	private static ClientProceduresInterface updater = null;
	
	public static void setSwingMode(ClientProceduresInterface Updater) {
		updater = Updater;
		terminal = null;
		stateManager = null;
	}
	
	public static void setCLIMode(StateManagerCLIInterface StateManager, Terminal Terminal) {
		updater = null;
		terminal = Terminal;
		stateManager = StateManager;
	}
	
	private static ClientRemoteInterface createClient() {
		if (updater == null) {
			return new ClientRemoteTerminal(stateManager, terminal);
		} else if (terminal == null && stateManager == null) {
			return new ClientRemoteSwing(updater);
		} else {
			crash("Invalid rmi-launcher state");
		}
		return null;
	}
	
	public static ProxyToServer initializer(ClientLocalSettings localSettings) {
		try {
			ipAddress = localSettings.getDestinationServerAddress();
			ClientRemoteInterface client = createClient();
			UnicastRemoteObject.exportObject(client, 0);
			Naming.rebind("//localhost/Client", client);
			
			Registry remoteRegistry = LocateRegistry.getRegistry(ipAddress);
			ServerRemoteInterface serverStub = (ServerRemoteInterface) remoteRegistry.lookup("Server");

			// now we will only use this proxy to talk to the server
			ProxyToServer serverProxy = new ProxyToServer(client, serverStub);
			
			serverProxy.registerClient(client);
			
			return serverProxy;
			
		} catch (RemoteException e) {
			crash("Remote exception " + e.getMessage());
		} catch (MalformedURLException e) {
			crash("Malformed URL exception " + e.getMessage());
		} catch (NotBoundException e) {
			crash("Not Bound exception " + e.getMessage());
		}
		return null;
	}
	
	/**
	 * Stops the program in case of unrecoverable errors
	 * @param message
	 */
	private static void crash(String message) {
		LOG.severe(message);
		throw new AssertionError();
	}

}
