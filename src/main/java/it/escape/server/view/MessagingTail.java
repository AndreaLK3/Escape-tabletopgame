package it.escape.server.view;

public interface MessagingTail {

	public void tailRead();
	
	public void tailWrite(String message);
	
	public void setConnectionAlive();
	
	public void setConnectionDead();
}
