package it.escape.client.controller;

import java.util.Observable;

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
