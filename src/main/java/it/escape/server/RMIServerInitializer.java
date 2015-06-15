package it.escape.server;

import it.escape.server.view.rmispecific.ServerRMI;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.logging.Logger;

public class RMIServerInitializer {
protected static final Logger log = Logger.getLogger( RMIServerInitializer.class.getName() );
	
	private ServerLocalSettings locals;

	public RMIServerInitializer(ServerLocalSettings locals) {
		this.locals = locals;	
		try {
			MapCreator mapCreator = new MapCreator("resources/Galilei.json");
			Master.setMapCreator(mapCreator);
			ServerRMI.initializer(this.locals, locals.getServerPort());
			
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
