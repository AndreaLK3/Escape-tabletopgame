package it.escape.rmitestbed;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements ServerRemote {
	
	private ClientRemote client;

	/**
	 * method called by the client
	 */
	public void registerClient(ClientRemote client) throws RemoteException {
		System.out.println("Client registered");
		this.client = client;
		callBackMaybe();
		client.harakiri();
	}
	
	/**
	 * method called by the client
	 */
	public void sendAnswer(String answer) throws RemoteException {
		System.out.println("Client says: " + answer);
	}
	
	/**
	 * Call a remote method in the client, even though technically
	 * *we* are the server
	 */
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
