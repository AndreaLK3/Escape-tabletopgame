package it.escape.core.client.connection.rmi;

import java.rmi.RemoteException;

/**
 * This abstract class implements the ping() function of the client
 * interface. It instantly pongs back the server, notifying it that
 * we are still connected
 * @author michele
 *
 */
public abstract class RMIPingBack implements ClientRemoteInterface {
	
	private ProxyToServer remoteServer = null;
	
	public RMIPingBack() {
		
	}
	
	public void bindServer(ProxyToServer remoteServer) {
		this.remoteServer = remoteServer;
	}

	public void ping() throws RemoteException {
		if (remoteServer != null) {
			remoteServer.pong();
		}
	}
	
}
