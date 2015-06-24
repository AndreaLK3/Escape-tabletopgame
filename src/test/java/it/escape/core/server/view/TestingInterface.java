package it.escape.core.server.view;

import it.escape.core.server.view.MessagingChannelStrings;

public class TestingInterface extends MessagingChannelStrings {
	
	private String last_message;
	
	public TestingInterface() {
		super();
	}
	
	@Override
	public void sendToClient(String singleMessage) {
		last_message = singleMessage;
	}
	
	public void receiveFromClient(String in) {
		enqueueFacility(in);
		afterTailWrite();
	}

	public String getLast_message() {
		return last_message;
	}
	
}
