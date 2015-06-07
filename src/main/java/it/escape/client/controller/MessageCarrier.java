package it.escape.client.controller;

import java.util.Observable;

/**When a new String arrives to ClientSocketChannel,
 * the method newMessage (String message) inside this class is invoked;
 * the method then invokes notifyObservers().
 * This class is observed by UpdaterCLI, that will 
 * process the message.*/
public class MessageCarrier extends Observable {
	
	private String message;

	public MessageCarrier() {
		super();
	}
	
	public void newMessage(String message) {
		this.message = message;
		setChanged();
		notifyObservers();
	}

	public String getMessage() {
		return message;
	}
	
}
