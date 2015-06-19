package it.escape.core.server.view;

import java.util.Observer;

public interface MessagingChannelInterface {
	
	public void addObserver(Observer o);
	
	public void killConnection();
}
