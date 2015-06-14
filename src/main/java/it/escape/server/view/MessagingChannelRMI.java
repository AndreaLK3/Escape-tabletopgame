package it.escape.server.view;

import java.util.Observer;

import it.escape.client.ClientRemoteInterface;

public class MessagingChannelRMI implements ServerToChannelRMI, MessagingChannelInterface {
	
	private ClientRemoteInterface client;
	
	private ServerRemoteInterface server;
	
	private String defaultOption;
	
	private String answer;
	
	public MessagingChannelRMI(ClientRemoteInterface client, ServerRemoteInterface server) {
		this.client = client;
		this.server = server;
	}

	public ClientRemoteInterface getClient() {
		return client;
	}
	
	public synchronized String getAnswer() {
		try {
			wait();
		} catch (InterruptedException e) {
		}
		return answer;
	}
	
	public void setDefaultOption(String option) {
		defaultOption = option;
	}

	public synchronized void setAnswer(String answer) {
		this.answer = answer;
		notify();
	}
	
	public synchronized void overrideDefault() {
		answer = defaultOption;
		notify();
	}

	/** here for compatibility across MessagingChannels, it doesn't
	 * actually do anything. */
	public void addObserver(Observer o) {
	}

	public void killConnection() {
		server.unregisterClient(client);
	}
}
