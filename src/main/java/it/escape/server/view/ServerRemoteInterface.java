package it.escape.server.view;

import it.escape.client.ClientRemoteInterface;

import java.rmi.Remote;

public interface ServerRemoteInterface extends Remote {
	
	public void registerClient (ClientRemoteInterface client);
	
	public void unregisterClient (ClientRemoteInterface client);
	
	public void rename(String message);
	
	public void globalChat(String message);
	
	public void whoAmI();
	
	public void whereAmI();
	
	public void setAnswer(String answer);
}
