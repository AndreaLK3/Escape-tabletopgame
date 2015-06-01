package it.escape.server.view;

public interface MessagingTail {

	public void sendToClient(String singleMessage);
	
	public void receiveFromClient();
	
	public void setConnectionAlive();
	
	public void setConnectionDead();
	
	public void killConnection();
}
