package it.escape.rmitestbed;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Client implements ClientRemote {
	
	private static ClientRemote client;
	
	public Client() throws RemoteException {
        super();
    }

	public void askQuestion(String message) throws RemoteException {
		System.out.println("Server asks: " + message);
	}
	
	/**
	 * The JVM won't stop until all the object are unexported
	 */
	public void harakiri() throws RemoteException {
		System.out.println("So long, and thanks for all the sakana");
		UnicastRemoteObject.unexportObject(client, true);
	}
	
	public static void main(String[] args) {
		try {
			// for local testing, we must comment this, as the registry is already
			// set up by the server application
			//LocateRegistry.createRegistry(1099);
			client = new Client();
			UnicastRemoteObject.exportObject(client, 0);
			Naming.rebind("//localhost/Client", client);
			System.out.println("Client started");
			
			Registry remote = LocateRegistry.getRegistry("127.0.0.1");
			ServerRemote serverStub = (ServerRemote) remote.lookup("Server");
			
			System.out.println("Calling...");
			serverStub.sendAnswer("Lol, ho risposto prima della domanda");;
			
			System.out.println("Registering...");
			serverStub.registerClient(client);
			
			
		} catch (RemoteException e) {
			System.out.println("Remote exception " + e.getMessage());
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL exception " + e.getMessage());
		} catch (NotBoundException e) {
			System.out.println("Not Bound exception " + e.getMessage());
		}
	}


}
