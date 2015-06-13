package it.escape.rmitestbed;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ExampleClientRemote extends Remote {
	
	public void askQuestion(String message) throws RemoteException;
	
	public void harakiri() throws RemoteException;
	
}
