package it.escape.server;

import it.escape.server.view.sockspecific.Server;

import java.io.IOException;
import java.util.logging.Logger;

/**The class that is used to start the Server*/
public class SockServerInitializer {
	
	protected static final Logger log = Logger.getLogger( SockServerInitializer.class.getName() );
	
	private ServerLocalSettings locals;
	
	private Server server;

	public SockServerInitializer(ServerLocalSettings locals) {
		this.locals = locals;
		try {
			MapCreator mapCreator = new MapCreator("resources/Galilei.json");
			Master.setMapCreator(mapCreator);
			server = Server.createServerInstance(locals);
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
