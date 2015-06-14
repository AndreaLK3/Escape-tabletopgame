package it.escape.server.view;

import it.escape.client.ClientRemoteInterface;
import it.escape.rmitestbed.ExampleClientRemote;
import it.escape.rmitestbed.ExampleServer;
import it.escape.rmitestbed.ExampleServerRemote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerRMI implements ServerRemoteInterface {

	private List<ClientRemoteInterface> clientsList;
	
	
	@Override
	public void registerClient(ClientRemoteInterface client) {
		clientsList.add(client);
		//TODO: send motd 
		
	}

	@Override
	public void unregisterClient(ClientRemoteInterface client) {
		clientsList.remove(client);
		
	}

	@Override
	public void rename(String message, ClientRemoteInterface client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void globalChat(String message, ClientRemoteInterface client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void whoAmI(ClientRemoteInterface client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void whereAmI(ClientRemoteInterface client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAnswer(String answer, ClientRemoteInterface client) {
		// TODO Auto-generated method stub
		
	}
	
	/**This method sets up the Registry and creates and exposes the Server Remote Object;
	 *  after the Server invoked this, the clients using RMI will be able to invoke functions.*/
	public static void initializer() {
		try {
			LocateRegistry.createRegistry(1099);
			ServerRemoteInterface server = new ServerRMI();
			UnicastRemoteObject.exportObject(server, 0);
			Naming.rebind("//localhost/Server", server);
			
		} catch (RemoteException e) {
			System.out.println("Remote exception " + e.getMessage());
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL exception " + e.getMessage());
		}
	}
	
	/**Constructor for the object*/
	public ServerRMI() {
		clientsList = new ArrayList<ClientRemoteInterface>();
	}

}
