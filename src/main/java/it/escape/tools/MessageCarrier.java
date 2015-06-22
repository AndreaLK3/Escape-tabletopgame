package it.escape.tools;

import java.util.Observable;

/**
 * Simple observable, carrying a single message string.
 * It's intended to be used by other bigger classes to
 * deliver messages through the observer pattern.
 * The container class must implement observable as well,
 * but override addObserver() to redirect the call to MessageCarrier.
 */
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
