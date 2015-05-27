package it.escape.server.controller;

import it.escape.server.model.game.Announcer;
import it.escape.strings.StringRes;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;

public class TestingAnnouncerObserver implements Observer {
	
	private String last_message;

	public TestingAnnouncerObserver() {
	}

	public void update(Observable o, Object arg) {
		if (o instanceof Announcer) {
			Announcer a = (Announcer) o;
			last_message = a.getMessage();
		}
	}

	public String getLast_message() {
		return last_message;
	}
	
}
