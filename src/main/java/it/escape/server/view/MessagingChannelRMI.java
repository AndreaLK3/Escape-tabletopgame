package it.escape.server.view;

import it.escape.client.ClientRemoteInterface;

public class MessagingChannelRMI {
	
	private ClientRemoteInterface client;
	
	private String defaultOption;
	
	public ClientRemoteInterface getClient() {
		return client;
	}
	
	public String getAnswer() {
		return null;
	}
	
	public void setDefaultOption(String option) {
		defaultOption = option;
	}

}
