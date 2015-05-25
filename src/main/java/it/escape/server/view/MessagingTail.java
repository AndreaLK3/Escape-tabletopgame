package it.escape.server.view;

public interface MessagingTail {

	public void tailRead(String singleMessage);
	
	public void tailWrite();
	
	public void setConnectionAlive();
	
	public void setConnectionDead();
}
