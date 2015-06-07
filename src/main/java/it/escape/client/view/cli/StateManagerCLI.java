package it.escape.client.view.cli;

import it.escape.client.controller.cli.StateManagerCLIInterface;


/**This class contains:
 * 1) the variable TurnInputState currentState, 
 * to keep track of what the turn is asking the user to do.
 * 2) The methods to set the currentState,
 * 3) The variable myTurn (true/false)
 * */
public class StateManagerCLI implements StateManagerCLIInterface {

	private TurnInputStates currentState;  // input required
	
	private boolean myTurn;  // my turn vs not my turn

	public StateManagerCLI () {
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
