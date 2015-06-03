package it.escape.client.controller;

public class StateManager {

	private TurnInputStates currentState;  // input required
	
	private boolean myTurn;  // my turn vs not my turn

	public StateManager () {
		myTurn = false;
		setFreeState();
		
	}
	
	public void setMyTurn() {
		myTurn = true;
	}
	
	public void setNotMyTurn() {
		myTurn = false;
	}
	
	public boolean isMyTurn() {
		return myTurn;
	}
	
	public void setFreeState() {
		
		currentState = TurnInputStates.FREE;
	}
	
	public void setObjectCardState() {
		
		currentState = TurnInputStates.OBJECTCARD;
	}
	
	public void setPositionState() {
		
		currentState = TurnInputStates.POSITION;
	}
	
	public void setYesNoState() {
		
		currentState = TurnInputStates.YES_NO;
	}
	

	
	public TurnInputStates getCurrentState() {
		return currentState;
	
	}
}
