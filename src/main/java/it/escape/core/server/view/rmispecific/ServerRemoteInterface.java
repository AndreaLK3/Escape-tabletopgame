package it.escape.core.server.view.rmispecific;

import it.escape.core.client.connection.rmi.ClientRemoteInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRemoteInterface extends Remote {
	
	public void registerClient (ClientRemoteInterface client) throws RemoteException;
	
	public void unregisterClient (ClientRemoteInterface client) throws RemoteException;
	
	public void rename(String message, ClientRemoteInterface client) throws RemoteException;
	
	public void globalChat(String message, ClientRemoteInterface client) throws RemoteException;
	
	public void whoAmI(ClientRemoteInterface client) throws RemoteException;
	
	public void whereAmI(ClientRemoteInterface client) throws RemoteException;
	
	public void setAnswer(String answer, ClientRemoteInterface client) throws RemoteException;
	
	public void pong(ClientRemoteInterface client) throws RemoteException;
}
