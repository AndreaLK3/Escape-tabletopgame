package it.escape.server.view.rmispecific;

import it.escape.client.view.connection.rmi.ClientRemoteInterface;

import java.rmi.Remote;

public interface ServerRemoteInterface extends Remote {
	
	public void registerClient (ClientRemoteInterface client);
	
	public void unregisterClient (ClientRemoteInterface client);
	
	public void rename(String message, ClientRemoteInterface client);
	
	public void globalChat(String message, ClientRemoteInterface client);
	
	public void whoAmI(ClientRemoteInterface client);
	
	public void whereAmI(ClientRemoteInterface client);
	
	public void setAnswer(String answer, ClientRemoteInterface client);
}
