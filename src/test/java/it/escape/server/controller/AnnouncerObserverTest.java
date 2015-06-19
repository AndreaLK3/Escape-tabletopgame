package it.escape.server.controller;

import static org.junit.Assert.assertTrue;
import it.escape.core.server.model.AnnouncerStrings;

import java.util.Observable;
import java.util.Observer;

import org.junit.Test;

public class AnnouncerObserverTest implements Observer {
	
	private String last_message;

	public AnnouncerObserverTest() {
	}

	public void update(Observable o, Object arg) {
		if (o instanceof AnnouncerStrings) {
			AnnouncerStrings a = (AnnouncerStrings) o;
			last_message = a.getMessage();
		}
	}

	public String getLast_message() {
		return last_message;
	}
	
	@Test
	public void test() {
		assertTrue(true);
	}
	
}