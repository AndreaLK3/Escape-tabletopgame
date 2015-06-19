package it.escape.server;

import it.escape.utils.LogHelper;

import java.util.logging.Logger;

/**
 * This class implements a sequence of action to be run when the
 * server process receives a termination thread, i.e. an user presses
 *  Ctrl-C , or the server machine is shutting down
 * What we want to do here is to gracefully terminate all
 * the running games and disconnect all the users.
 * @author michele
 *
 */
public class ServerShutdownHook implements Runnable {
	
	private static final Logger LOG = Logger.getLogger( ServerShutdownHook.class.getName() );

	public ServerShutdownHook() {
		LogHelper.setDefaultOptions(LOG);
	}

	public void run() {
		LOG.info("Running server shutdown hook.");
		Master.stopAll();
		/*
		 * For some reasons, JVM closes the loggers halfway through,
		 * so this message is necessary to diagnose the correct
		 * execution of the shutdown sequence
		 */
		System.out.println("Shutdown sequence completed");
	}

}
