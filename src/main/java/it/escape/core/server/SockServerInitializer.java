package it.escape.core.server;

import it.escape.core.server.view.sockspecific.ServerSocketCore;
import it.escape.tools.GlobalSettings;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Our ServerSocketCore is actually composed of 2 classes:
 * ServerSocketCore that uses the Socket connection +
 * ServerSocketCore that handles the RMI connection.
 * This is the class that is used to start the ServerSocketCore that receives Socket connections.
 * [note: Users who utilize different connection types can play at the same game;
 * There is only one Master that handles all the connections and redirects the user to the gameMasters.]*/
public class SockServerInitializer {
	
	protected static final Logger LOGGER = Logger.getLogger( SockServerInitializer.class.getName() );
	
	private GlobalSettings locals;
	
	private static final int MAX_RETRIES = 5;
	
	private ServerSocketCore server;
	
	private int retries;

	public SockServerInitializer() {
		retries = 0;
	}
	
	private void retry_start(IOException e) {
		if (retries > MAX_RETRIES) {
			crash(e.getMessage());
		}
		locals.setServerPort(locals.getServerPort() + 1);
		startSocketServer(locals);
		retries++;
	}
	
	public void startSocketServer(GlobalSettings locals) {
		this.locals = locals;
		try {
			MapCreator mapCreator = new MapCreator(locals.getMaprotation());
			Master.setMapCreator(mapCreator);
			server = ServerSocketCore.createServerInstance(this.locals);
			server.run();
			
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unsuccessful server startup, trying again with a different port...", e);
			retry_start(e);
		}
	}
	
	/**
	 * Stops the program in case of unrecoverable errors
	 * @param message
	 */
	private void crash(String message) {
		LOGGER.severe(message);
		throw new AssertionError();
	}
}
