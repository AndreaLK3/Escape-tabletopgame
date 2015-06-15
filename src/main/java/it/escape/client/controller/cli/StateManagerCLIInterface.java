package it.escape.client.controller.cli;

public interface StateManagerCLIInterface {
	
	public void setFreeState();
	
	public void setObjectCardState();
	
	public void setPositionState();
	
	public void setYesNoState();
	
	public void setMyTurn();
	
	public void setNotMyTurn();
	
	public void setOtherChoice();
}
