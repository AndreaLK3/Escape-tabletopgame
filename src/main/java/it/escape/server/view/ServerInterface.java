package it.escape.server.view;

import it.escape.server.ServerLocalSettings;
import it.escape.server.view.sockspecific.Connection;

public interface ServerInterface {
	
	public void unregisterConnection(Connection connection);
	
	public ServerLocalSettings getLocals();
}
