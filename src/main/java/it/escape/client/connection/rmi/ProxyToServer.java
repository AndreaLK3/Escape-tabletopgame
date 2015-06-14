package it.escape.client.connection.rmi;

import it.escape.server.view.rmispecific.ServerRemoteInterface;

/**
 * Proxy to the server remote methods, this middle class
 * is necessary, as it provides autenthication (needed by
 * the server to know which client is talking)
 * @author michele
 *
 */
public class ProxyToServer implements ServerRemoteInterface {

	private ClientRemoteInterface self;
	
	private ServerRemoteInterface server;
	
	public ProxyToServer(ClientRemoteInterface self,
			ServerRemoteInterface server) {
		this.self = self;
		this.server = server;
	}

	// encapsule and enrich the inherited methods
	
	public void registerClient() {
		registerClient(self);
	}

	public void unregisterClient() {
		unregisterClient(self);
	}

	public void rename(String message) {
		rename(message, self);
	}

	public void globalChat(String message) {
		globalChat(message, self);
	}

	public void whoAmI() {
		whoAmI(self);
	}

	public void whereAmI() {
		whereAmI(self);
	}

	public void setAnswer(String answer) {
		setAnswer(answer, self);
	}
	
	// inherited by ServerRemoteInterface
	public void registerClient(ClientRemoteInterface client) {
		server.registerClient(client);
	}

	public void unregisterClient(ClientRemoteInterface client) {
		server.unregisterClient(client);
	}

	public void rename(String message, ClientRemoteInterface client) {
		server.rename(message, client);
	}

	public void globalChat(String message, ClientRemoteInterface client) {
		server.globalChat(message, client);
	}

	public void whoAmI(ClientRemoteInterface client) {
		server.whoAmI(client);
	}

	public void whereAmI(ClientRemoteInterface client) {
		server.whereAmI(client);
	}

	public void setAnswer(String answer, ClientRemoteInterface client) {
		server.setAnswer(answer, client);
	}

}
