package it.escape.client.model;

/**This class in client.model defines:
 * Name, Status, LastNoiseLocation of a Player.
 * It is updated by the UpdaterSwing in the Controller, 
 * according to the messages the Server sends.
 * POSSIBLE IMPLEMENTATION: The view observes this class and updates the Swing view.
 */
public class PlayerState {
	
	protected String myName;
	
	protected CurrentPlayerStatus myStatus;
	
	protected String lastNoiseLocation;
	


	public PlayerState() {
		
	};
	
	public String setMyName() {
		return myName;
	}
	

	public String getMyName() {
		return myName;
	}
	
	public String getLastNoiseLocation() {
		return lastNoiseLocation;
	}


	public void setLastNoiseLocation(String lastNoiseLocation) {
		this.lastNoiseLocation = lastNoiseLocation;
	}


	public void setMyStatus(CurrentPlayerStatus myStatus) {
		this.myStatus = myStatus;
	}
	
	public CurrentPlayerStatus getMyStatus() {
		return myStatus;
	}

	
}
