package it.escape.client.connection.rmi;

import java.rmi.RemoteException;
import java.util.logging.Logger;

import it.escape.client.connection.BindDisconnectCallbackInterface;
import it.escape.client.connection.DisconnectedCallbackInterface;
import it.escape.client.controller.ClientStringChannelInterface;
import it.escape.server.view.rmispecific.ServerRemoteInterface;

/**
 * Proxy to the server remote methods, this middle class
 * is necessary, as it provides autenthication (needed by
 * the server to know which client is talking)
 * This class will also act as an interface for Relay to
 * send strings to.
 * This class will interpret any rmi errors as a server-side
 * disconnetion, and will notify the subscriber
 * @author michele
 *
 */
public class ProxyToServer implements ServerRemoteInterface, ClientStringChannelInterface, BindDisconnectCallbackInterface {
	
	protected static final Logger LOG = Logger.getLogger( ProxyToServer.class.getName() );

	private ClientRemoteInterface self;
	
	private ServerRemoteInterface server;
	
	private DisconnectedCallbackInterface disconnectCallback;
	
	public ProxyToServer(ClientRemoteInterface self,
			ServerRemoteInterface server) {
		this.self = self;
		this.server = server;
	}
	
	public void bindDisconnCallback(
			DisconnectedCallbackInterface disconnectCallback) {
		this.disconnectCallback = disconnectCallback;
	}
	
	private void notifyDisconnected() {
		disconnectCallback.disconnected();
	}
	
	public void killConnection() {
		// TODO Unsubscribe from server
	}

	public void sendMessage(String msg) {
		setAnswer(msg);
	}

	// encapsule and enrich the inherited methods
	
	public void registerClient() {
		try {
			registerClient(self);
		} catch (RemoteException e) {
			notifyDisconnected();
			LOG.warning("Cannot register the client: " + e.getMessage());
		}
	}

	public void unregisterClient() {
		try {
			unregisterClient(self);
		} catch (RemoteException e) {
			notifyDisconnected();
			LOG.warning("Cannot unregister the client: " + e.getMessage());
		}
	}

	public void rename(String message) {
		try {
			rename(message, self);
		} catch (RemoteException e) {
			notifyDisconnected();
			LOG.warning("Cannot rename: " + e.getMessage());
		}
	}

	public void globalChat(String message) {
		try {
			globalChat(message, self);
		} catch (RemoteException e) {
			notifyDisconnected();
			LOG.warning("Cannot send chat message: " + e.getMessage());
		}
	}

	public void whoAmI() {
		try {
			whoAmI(self);
		} catch (RemoteException e) {
			notifyDisconnected();
			LOG.warning("Cannot send whoami: " + e.getMessage());
		}
	}

	public void whereAmI() {
		try {
			whereAmI(self);
		} catch (RemoteException e) {
			notifyDisconnected();
			LOG.warning("Cannot send whereami: " + e.getMessage());
		}
	}

	public void setAnswer(String answer) {
		try {
			setAnswer(answer, self);
		} catch (RemoteException e) {
			notifyDisconnected();
			LOG.warning("Cannot set the answer: " + e.getMessage());
		}
	}
	
	// inherited by ServerRemoteInterface
	public void registerClient(ClientRemoteInterface client) throws RemoteException {
		server.registerClient(client);
	}

	public void unregisterClient(ClientRemoteInterface client) throws RemoteException {
		server.unregisterClient(client);
	}

	public void rename(String message, ClientRemoteInterface client) throws RemoteException {
		server.rename(message, client);
	}

	public void globalChat(String message, ClientRemoteInterface client) throws RemoteException {
		server.globalChat(message, client);
	}

	public void whoAmI(ClientRemoteInterface client) throws RemoteException {
		server.whoAmI(client);
	}

	public void whereAmI(ClientRemoteInterface client) throws RemoteException {
		server.whereAmI(client);
	}

	public void setAnswer(String answer, ClientRemoteInterface client) throws RemoteException {
		server.setAnswer(answer, client);
	}

}
