package it.escape.server.view;

import it.escape.client.ClientRemoteInterface;

public class MessagingChannelRMI implements ServerToChannelRMI {
	
	private ClientRemoteInterface client;
	
	private String defaultOption;
	
	private String answer;
	
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

}
