package it.escape.server.controller;

import static org.junit.Assert.*;

import java.util.Observable;
import java.util.Observer;

import org.junit.Test;

import it.escape.server.model.Announcer;
import it.escape.server.model.AnnouncerStrings;

public class AnnouncerObserverTest implements Observer {
	
	private String last_message;

	public AnnouncerObserverTest() {
	}

	public void update(Observable o, Object arg) {
		if (o instanceof AnnouncerStrings) {
			Announcer a = (Announcer) o;
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