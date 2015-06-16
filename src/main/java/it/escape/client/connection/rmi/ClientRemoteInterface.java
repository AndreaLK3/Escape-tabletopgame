package it.escape.client.connection.rmi;

import it.escape.client.controller.gui.ClientProceduresInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRemoteInterface extends Remote, ClientProceduresInterface {

	public abstract void showMessageInTerminal (String message) throws RemoteException;
	
	public abstract void setID(int clientID) throws RemoteException;
	
	public abstract int getID() throws RemoteException;
	
}
