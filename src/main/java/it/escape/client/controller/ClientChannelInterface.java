package it.escape.client.controller;

public interface ClientChannelInterface {
	
	public void killConnection();
	
	public void sendMessage(String msg);
}
