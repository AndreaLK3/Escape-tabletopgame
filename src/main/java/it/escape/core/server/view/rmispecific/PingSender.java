package it.escape.core.server.view.rmispecific;

import it.escape.core.client.connection.rmi.ClientRemoteInterface;
import it.escape.core.server.view.MessagingChannelRMI;
import it.escape.tools.utils.LogHelper;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Runnable that regularly pings the clients, clients
 * who don't respond on time are considered "lost" and removed
 * @author michele
 *
 */
public class PingSender implements Runnable {
	
	private static final Logger LOGGER = Logger.getLogger( ServerRMICore.class.getName() );
	
	private static final int SLEEP = 3000;
	
	private boolean running = true;
	
	private boolean restarted;
	
	private List<MessagingChannelRMI> clientsList;
	
	private Map<MessagingChannelRMI, Boolean> answered;
	
	private ServerRMICore server;
	
	public PingSender(List<MessagingChannelRMI> clientsList, ServerRMICore server) {
		LogHelper.setDefaultOptions(LOGGER);
		this.clientsList = clientsList;
		this.server = server;
	}

	@Override
	public synchronized void run() {
		while (running) {
			restarted = false;
			resetAnswers();
			sendPings();
			try {
				wait(SLEEP);
			} catch (InterruptedException e) {
			}
			if (running && !restarted) {
				checkAnswers();
			}
			
		}
	}
	
	public synchronized void restart() {
		notify();
		restarted = true;
	}
	
	public void stopPinging() {
		running = false;
	}
	
	private void resetAnswers() {
		answered = new HashMap<MessagingChannelRMI, Boolean>();
		for (MessagingChannelRMI chan : clientsList) {
			answered.put(chan, false);
		}
	}
	
	private void sendPings() {
		List<MessagingChannelRMI> temp = new ArrayList<MessagingChannelRMI>(clientsList);
		for (MessagingChannelRMI chan : temp) {
			ClientRemoteInterface cli = chan.getClient();
			try {
				cli.ping();
			} catch (RemoteException e) {
				LOGGER.warning("client " + chan.getClient().toString() + " can't be pinged");
				server.clientLost(chan);
			}
		}
	}
	
	public void getPong(ClientRemoteInterface cli) {
		answered.put(server.findChannel(cli), true);
	}
	
	private void checkAnswers() {
		boolean esit;
		List<MessagingChannelRMI> temp = new ArrayList<MessagingChannelRMI>(clientsList);
		for (MessagingChannelRMI chan : temp) {
			try {
				esit = answered.get(chan);
			} catch (NullPointerException e) {
				esit = false;
			}
			if (!esit) {
				LOGGER.warning("client " + chan.getClient().toString() + " didn't ping back");
				server.clientLost(chan);
			}
		}
	}
	
}
