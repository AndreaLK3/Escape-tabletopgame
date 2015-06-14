package it.escape.server.view;

import it.escape.client.ClientRemoteInterface;
import it.escape.rmitestbed.ExampleClientRemote;
import it.escape.rmitestbed.ExampleServer;
import it.escape.rmitestbed.ExampleServerRemote;
import it.escape.server.Master;
import it.escape.server.ServerLocalSettings;
import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.UserMessagesReporterRMI;
import it.escape.server.controller.UserMessagesReporterSocket;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerRMI implements ServerRemoteInterface {

	// it's more useful to list the MessagingChannelRMI, which match 1:1 to the active clients
	private List<MessagingChannelRMI> clientsList;
	
	private ServerLocalSettings locals;
	
	private MessagingChannelRMI findChannel(ClientRemoteInterface client) {
		for (MessagingChannelRMI c : clientsList) {
			if (c.getClient() == client) {
				return c;
			}
		}
		return null;
	}
	
	@Override
	public void registerClient(ClientRemoteInterface client) {
		MessagingChannelInterface channel = new MessagingChannelRMI(client, this);
		clientsList.add((MessagingChannelRMI) channel);
		UserMessagesReporterSocket.createUMR(channel);
		Master.newPlayerHasConnected(channel, locals);
		//TODO: send motd 
		
	}

	@Override
	public void unregisterClient(ClientRemoteInterface client) {
		MessagingChannelRMI del = findChannel(client);
		if (del != null) {
			clientsList.remove(del);
		}
	}

	@Override
	public void rename(String message, ClientRemoteInterface client) {
		// get the player reference from UMR
		// get the 
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
		//UserMessagesReporter.getReporterInstance(interfaceWithUser)
	}
	
	/**This method sets up the Registry and creates and exposes the Server Remote Object;
	 *  after the Server invoked this, the clients using RMI will be able to invoke functions.*/
	public static void initializer(ServerLocalSettings locals) {
		try {
			LocateRegistry.createRegistry(1099);
			ServerRemoteInterface server = new ServerRMI(locals);
			UnicastRemoteObject.exportObject(server, 0);
			Naming.rebind("//localhost/Server", server);
			
		} catch (RemoteException e) {
			System.out.println("Remote exception " + e.getMessage());
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL exception " + e.getMessage());
		}
	}
	
	/**Constructor for the object*/
	public ServerRMI(ServerLocalSettings locals) {
		this.locals = locals;
		clientsList = new ArrayList<MessagingChannelRMI>();
	}

}
