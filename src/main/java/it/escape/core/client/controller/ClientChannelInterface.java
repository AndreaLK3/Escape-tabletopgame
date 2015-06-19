package it.escape.core.client.controller;

public interface ClientChannelInterface {
	
	public void killConnection();
	
	public void sendMessage(String msg);
}
