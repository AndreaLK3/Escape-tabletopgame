package it.escape.core.client.connection.rmi;

import it.escape.core.client.controller.cli.StateManagerCLIInterface;
import it.escape.core.client.controller.gui.ClientProceduresInterface;
import it.escape.core.client.view.cli.Terminal;
import it.escape.core.server.SockServerInitializer;
import it.escape.core.server.view.rmispecific.ServerRemoteInterface;
import it.escape.tools.GlobalSettings;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

/**This static class initializes one of the 2 kinds of ClientRemote for RMI, 
 * which can use the Swing View or the Terminal View */
public class ClientRemoteInitializer {
	
	private static final Logger LOG = Logger.getLogger( ClientRemoteInitializer.class.getName() );
	
	private static String ipAddress;
	
	private static Terminal terminal = null;
	
	private static StateManagerCLIInterface stateManager = null;
	
	private static ClientProceduresInterface updater = null;
	
	private static ProxyToServer serverProxy;
	
	private static ClientRemoteInterface client;
	
	/**This method, invoked by ClientInitializerGUIRMI, stores the reference
	 * to the UpdaterSwing object.*/
	public static void setSwingMode(ClientProceduresInterface Updater) {
		updater = Updater;
		terminal = null;
		stateManager = null;
	}
	
	/**This method, invoked by ClientInitializerCLIRMI, stores the reference
	 * to the Terminal object and the StateManager object.*/
	public static void setCLIMode(StateManagerCLIInterface StateManager, Terminal Terminal) {
		updater = null;
		terminal = Terminal;
		stateManager = StateManager;
	}
	
	/***/
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
	
	public static ProxyToServer initializer(GlobalSettings localSettings) {
		try {
			ipAddress = localSettings.getDestinationServerAddress();
			client = createClient();
			UnicastRemoteObject.exportObject(client, 0);
			Naming.rebind("//localhost/Client", client);
			
			Registry remoteRegistry = LocateRegistry.getRegistry(ipAddress);
			ServerRemoteInterface serverStub = (ServerRemoteInterface) remoteRegistry.lookup("ServerSocketCore");

			// now we will only use this proxy to talk to the server
			serverProxy = new ProxyToServer(client, serverStub);
			// 
			((RMIPingBack) client).bindServer(serverProxy);
			
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
	
	public static void stopRMI() {
		try {
			UnicastRemoteObject.unexportObject(client, true);
		} catch (NoSuchObjectException e) {
			crash("Could not unexport the client: " + e.getMessage());
		}
	}
	
	public static void postponedStart() {
		try {
			serverProxy.registerClient(client);
		} catch (RemoteException e) {
			crash("cannot register to server! " + e.getMessage());
		}
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