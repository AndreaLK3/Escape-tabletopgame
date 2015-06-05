package it.escape.server.view;

import it.escape.server.Master;
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

	@Override
	public void run() {
		LOG.info("Running server shutdown hook.");
		Master.stopAll();
	}

}
