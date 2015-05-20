package it.escape.server.model.game;

import java.util.Observable;

/**
 * Announcer class: simple observable, relays a message to the observers,
 * located in the view (our net-communication threads).
 * Each running game will instance only one announcer
 * @author michele
 *
 */
public class Announcer extends Observable {
	
	private String message;
	
	public void announce(String message) {
		this.message = message;
		setChanged();
		notifyObservers();
	}

	public String getMessage() {
		return message;
	}
}
