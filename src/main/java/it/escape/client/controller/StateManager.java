package it.escape.client.controller;

import java.util.HashMap;
import java.util.Map;

public class StateManager {

	private TurnInputStates currentState;

	public StateManager () {
		setFreeState();
		
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
