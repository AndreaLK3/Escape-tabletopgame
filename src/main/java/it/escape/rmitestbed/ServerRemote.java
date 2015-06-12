package it.escape.rmitestbed;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRemote extends Remote {
	
	public void registerClient(ClientRemote client) throws RemoteException;
	
	public void sendAnswer(String answer) throws RemoteException;
}
