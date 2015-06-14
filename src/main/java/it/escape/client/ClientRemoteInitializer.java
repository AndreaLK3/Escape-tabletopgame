package it.escape.client;

import it.escape.client.view.ClientRemoteSwing;
import it.escape.server.view.ServerRemoteInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**This static class initializes one of the 2 kinds of ClientRemote, 
 * which can use the Swing View or the Terminal View */
public class ClientRemoteInitializer {
	
	private static String ipAddress;
	
	public static void initializer(ClientLocalSettings localSettings) {
		try {
			ipAddress = localSettings.getDestinationServerAddress();
			ClientRemoteInterface client = new ClientRemoteSwing(null);	//TODO: Differenziare Swing e Terminal
			UnicastRemoteObject.exportObject(client, 0);
			Naming.rebind("//localhost/Client", client);
			
			Registry remoteRegistry = LocateRegistry.getRegistry(ipAddress);
			ServerRemoteInterface serverStub = (ServerRemoteInterface) remoteRegistry.lookup("Server");

			// now we will only use this proxy to talk to the server
			ProxyToServer serverProxy = new ProxyToServer(client, serverStub);
			
			serverProxy.registerClient(client);
			
			
		} catch (RemoteException e) {
			System.out.println("Remote exception " + e.getMessage());
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL exception " + e.getMessage());
		} catch (NotBoundException e) {
			System.out.println("Not Bound exception " + e.getMessage());
		}
	}

}
