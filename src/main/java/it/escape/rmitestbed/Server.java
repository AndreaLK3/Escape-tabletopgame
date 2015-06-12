package it.escape.rmitestbed;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements ServerRemote {
	
	private ClientRemote client;

	public void registerClient(ClientRemote client) throws RemoteException {
		System.out.println("Clients registered");
		this.client = client;
		callBackMaybe();
		client.harakiri();
	}

	public void sendAnswer(String answer) throws RemoteException {
		System.out.println("Clients says: " + answer);
	}
	
	private void callBackMaybe() {
		try {
			client.askQuestion("What is the meaning of life?");
		} catch (RemoteException e) {
			System.out.println("Remote exception " + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1099);
			ServerRemote server = new Server();
			UnicastRemoteObject.exportObject(server, 0);
			Naming.rebind("//localhost/Server", server);
			System.out.println("Server started");
			
		} catch (RemoteException e) {
			System.out.println("Remote exception " + e.getMessage());
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL exception " + e.getMessage());
		}
	}

}
