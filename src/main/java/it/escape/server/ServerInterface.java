package it.escape.server;

import it.escape.server.view.sockspecific.Connection;

public interface ServerInterface {
	
	public void unregisterConnection(Connection connection);
	
	public ServerLocalSettings getLocals();
}
