package it.escape.core.server;

import it.escape.core.server.view.rmispecific.ServerRMICore;
import it.escape.tools.GlobalSettings;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.logging.Logger;

public class RMIServerInitializer {
protected static final Logger log = Logger.getLogger( RMIServerInitializer.class.getName() );
	
	private GlobalSettings locals;

	public RMIServerInitializer() {			
	}
	
	public void startRMIServer(GlobalSettings locals) {
		this.locals = locals;	
		try {
			MapCreator mapCreator = new MapCreator(locals.getMaprotation());
			Master.setMapCreator(mapCreator);
			ServerRMICore.initializer(this.locals);
			
		} catch (RemoteException e) {
			crash("RemoteException " + e.getMessage());
		} catch (MalformedURLException e) {
			crash("MalformedURLException " + e.getMessage());
		}
	}
	
	/**
	 * Stops the program in case of unrecoverable errors
	 * @param message
	 */
	private void crash(String message) {
		log.severe(message);
		throw new AssertionError();
	}
}
