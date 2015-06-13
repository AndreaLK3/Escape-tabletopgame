package it.escape.rmitestbed;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ExampleServerRemote extends Remote {
	
	public void registerClient(ExampleClientRemote client) throws RemoteException;
	
	public void sendAnswer(String answer) throws RemoteException;
}
