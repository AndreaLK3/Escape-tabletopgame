package it.escape.core.client.connection.rmi;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.escape.core.client.connection.BindDisconnectCallbackInterface;
import it.escape.core.client.connection.DisconnectedCallbackInterface;
import it.escape.core.client.controller.ClientChannelInterface;
import it.escape.core.server.view.rmispecific.ServerRemoteInterface;

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
public class ProxyToServer implements ServerRemoteInterface, ClientChannelInterface, BindDisconnectCallbackInterface {
	
	private static final Logger LOGGER = Logger.getLogger( ProxyToServer.class.getName() );

	private ClientRemoteInterface self;
	
	private ServerRemoteInterface server;
	
	private DisconnectedCallbackInterface disconnectCallback;
	
	private boolean expectConnectionAlive;
	
	public ProxyToServer(ClientRemoteInterface self,
			ServerRemoteInterface server) {
		this.self = self;
		this.server = server;
		expectConnectionAlive = true;
	}
	
	public void bindDisconnCallback(
			DisconnectedCallbackInterface disconnectCallback) {
		this.disconnectCallback = disconnectCallback;
	}
	
	private void notifyDisconnected() {
		expectConnectionAlive = false;
		disconnectCallback.disconnected();
	}
	
	public void killConnection() {
		expectConnectionAlive = false;
		unregisterClient();
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
			if (expectConnectionAlive) {
				LOGGER.log(Level.WARNING, "Cannot register the client", e);
			}
		}
	}

	public void unregisterClient() {
		try {
			unregisterClient(self);
		} catch (RemoteException e) {
			notifyDisconnected();
			if (expectConnectionAlive) {
				LOGGER.log(Level.WARNING, "Cannot unregister the client", e);
			}
		}
	}

	public void rename(String message) {
		try {
			rename(message, self);
		} catch (RemoteException e) {
			notifyDisconnected();
			if (expectConnectionAlive) {
				LOGGER.log(Level.WARNING, "Cannot rename", e);
			}
		}
	}

	public void globalChat(String message) {
		try {
			globalChat(message, self);
		} catch (RemoteException e) {
			notifyDisconnected();
			if (expectConnectionAlive) {
				LOGGER.log(Level.WARNING, "Cannot send chat message", e);
			}
		}
	}

	public void whoAmI() {
		try {
			whoAmI(self);
		} catch (RemoteException e) {
			notifyDisconnected();
			if (expectConnectionAlive) {
				LOGGER.log(Level.WARNING, "Cannot send whoami", e);
			}
		}
	}

	public void whereAmI() {
		try {
			whereAmI(self);
		} catch (RemoteException e) {
			notifyDisconnected();
			if (expectConnectionAlive) {
				LOGGER.log(Level.WARNING, "Cannot send whereami", e);
			}
		}
	}

	public void setAnswer(String answer) {
		try {
			setAnswer(answer, self);
		} catch (RemoteException e) {
			notifyDisconnected();
			if (expectConnectionAlive) {
				LOGGER.log(Level.WARNING, "Cannot set the answer", e);
			}
		}
	}
	
	public void pong(){
		try {
			pong(self);
		} catch (RemoteException e) {
			notifyDisconnected();
			if (expectConnectionAlive) {
				LOGGER.log(Level.WARNING, "Cannot pong the server", e);
			}
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

	public void pong(ClientRemoteInterface client) throws RemoteException {
		server.pong(client);
	}

}
