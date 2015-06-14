package it.escape.server.view.sockspecific;

import java.util.Observable;

/**
 * Companion-class for MessaingInterface, takes over the
 * Observable duties
 * @author michele
 *
 */
public class AsyncMessagingObservable extends Observable {
	
	private String message;

	public AsyncMessagingObservable() {
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
