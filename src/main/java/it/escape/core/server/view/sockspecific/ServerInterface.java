package it.escape.core.server.view.sockspecific;

import it.escape.tools.GlobalSettings;

public interface ServerInterface {
	
	public void unregisterConnection(Connection connection);
	
	public GlobalSettings getLocals();
}
