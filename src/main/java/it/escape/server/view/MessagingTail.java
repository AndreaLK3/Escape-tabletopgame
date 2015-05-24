package it.escape.server.view;

public interface MessagingTail {

	public void tailRead();
	
	public void tailWrite();
	
	public void setConnectionAlive();
	
	public void setConnectionDead();
}
