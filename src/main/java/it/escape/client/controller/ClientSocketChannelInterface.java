package it.escape.client.controller;

public interface ClientSocketChannelInterface {
	
	public void killConnection();
	
	public void sendMessage(String msg);
}
