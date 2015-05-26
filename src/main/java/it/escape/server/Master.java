package it.escape.server;

import it.escape.server.view.Server;

import java.io.IOException;
import java.util.logging.Logger;

public class Master {
	
	protected static final Logger log = Logger.getLogger( Master.class.getName() );
	
	private Server server;

	public Master() {
		try {
			server = Server.createServerInstance();
			
		} catch (IOException e) {
			crash(e.getMessage());
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
