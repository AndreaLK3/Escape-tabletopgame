package it.escape.server.view;

import it.escape.server.ServerLocalSettings;

public interface ServerInterface {
	
	public void unregisterConnection(Connection connection);
	
	public ServerLocalSettings getLocals();
}
