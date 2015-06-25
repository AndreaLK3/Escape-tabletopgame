package it.escape.core.server.controller;

import static org.junit.Assert.assertTrue;
import it.escape.core.server.model.AnnouncerStrings;
import it.escape.tools.strings.FormatToPattern;
import it.escape.tools.strings.StringRes;

import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class AnnouncerObserverTest implements Observer {
	
	private String last_message;
	
	private Pattern filter;

	public AnnouncerObserverTest() {
		filter = new FormatToPattern(StringRes.getString("messaging.playerConnected")).convert();
	}

	public void update(Observable o, Object arg) {
		if (o instanceof AnnouncerStrings) {
			AnnouncerStrings a = (AnnouncerStrings) o;
			Matcher check = filter.matcher(a.getMessage());
			if (check.matches()) {
				last_message = a.getMessage();
			}
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