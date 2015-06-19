package it.escape.server.view;

import it.escape.core.server.view.sockspecific.AsyncMessagingObservable;

import java.util.Observable;
import java.util.Observer;

public class TestingInterfaceObserver implements Observer {
	
	private String last_message;

	public TestingInterfaceObserver() {
	}

	public void update(Observable o, Object arg) {
		if (o instanceof AsyncMessagingObservable) {
			AsyncMessagingObservable a = (AsyncMessagingObservable) o;
			last_message = a.getMessage();
		}
	}

	public String getLast_message() {
		return last_message;
	}
	
}