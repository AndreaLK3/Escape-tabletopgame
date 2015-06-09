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
	
	// value to initialize lastNoiseLocation to
	protected static final String noNoise = "A00";

	public PlayerState() {
		myName = "Unknown";
		myStatus = CurrentPlayerStatus.DISCONNECTED;
		lastNoiseLocation = noNoise;
	}
	
	public PlayerState(String myName) {
		this.myName = myName;
		myStatus = CurrentPlayerStatus.CONNECTED;
		lastNoiseLocation = noNoise;
	}
	
	public PlayerState(String myName, CurrentPlayerStatus status) {
		this.myName = myName;
		myStatus = status;
		lastNoiseLocation = noNoise;
	}
	
	public PlayerState(String myName, String noiseLocation) {
		this.myName = myName;
		myStatus = CurrentPlayerStatus.ALIVE;  // if he makes a noise, then he must be alive!
		lastNoiseLocation = noiseLocation;
	}
	
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
