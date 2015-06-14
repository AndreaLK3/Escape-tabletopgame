package it.escape.server.view;

import java.util.Observer;

import it.escape.client.connection.rmi.ClientRemoteInterface;
import it.escape.server.view.rmispecific.ServerRemoteInterface;

/**
 * The class must be used this way:
 * The initialization is performed by the serverRMI
 * (1) The utilizer class will get the client reference,
 * and call its remote methods to ask questions,
 * (2) The the utilizer will call getAnswer(), which will
 * block the execution flow until the server has set the answer.
 * 
 * The override default mechanism works as usual.
 * @author michele
 *
 */
public class MessagingChannelRMI implements MessagingChannelInterface {
	
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

	/** here for compatibility across MessagingChannel(s), it doesn't
	 * actually do anything. */
	public void addObserver(Observer o) {
	}

	public void killConnection() {
		server.unregisterClient(client);
	}
}
