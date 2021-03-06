package it.escape.core.client.connection;

/**
 * Implemented by the view, the connection object will call
 * this metod after detecting a server-side disconnection
 * @author michele
 *
 */
public interface DisconnectedCallbackInterface {
	
	public void disconnected();
}
