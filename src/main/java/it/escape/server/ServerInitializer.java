package it.escape.server;

import it.escape.server.view.Server;

import java.io.IOException;
import java.util.logging.Logger;

/**The class that is used to start the Server*/
public class ServerInitializer {
	
	protected static final Logger log = Logger.getLogger( ServerInitializer.class.getName() );
	
	private Server server;

	public ServerInitializer() {
		try {
			MapCreator mapCreator = new MapCreator("resources/Galilei.json");
			Master.setMapCreator(mapCreator);
			server = Server.createServerInstance();
			server.run();
			
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
