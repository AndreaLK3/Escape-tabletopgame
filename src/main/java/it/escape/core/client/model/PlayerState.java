package it.escape.core.client.model;

import it.escape.tools.strings.StringRes;

/**This class in client.model defines:
 * Name, Status, LastNoiseLocation of a Player.
 * It is updated by the UpdaterSwing in the Controller, 
 * according to the messages the ServerSocketCore sends.
 */
public class PlayerState {
	
	protected String myName;
	
	protected CurrentPlayerStatus myStatus;
	
	protected String lastNoiseLocation;
	
	// value to initialize lastNoiseLocation to
	protected static final String NONOISE = StringRes.getString("client.applogic.unknownCoordinates");

	public PlayerState() {
		this.myName = "Unknown";
		myStatus = CurrentPlayerStatus.DISCONNECTED;
		lastNoiseLocation = NONOISE;
	}
	
	public PlayerState(String myName) {
		setMyName(myName);
		myStatus = CurrentPlayerStatus.CONNECTED;
		lastNoiseLocation = NONOISE;
	}
	
	public PlayerState(String myName, CurrentPlayerStatus status) {
		setMyName(myName);
		myStatus = status;
		//lastNoiseLocation = noNoise;
	}
	
	public PlayerState(String myName, String noiseLocation) {
		setMyName(myName);
		myStatus = CurrentPlayerStatus.ALIVE;  // if he makes a noise, then he must be alive!
		lastNoiseLocation = noiseLocation;
	}
	
	public void setMyName(String name) {
		if(name.equalsIgnoreCase(null)) {
			name = "Unknown"; 
		}
		myName = name;
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


	/**This functions updates the CurrentPlayerStatus, if and *only if* the priority
	 * level of the proposed status is >= than the priority level of the current status.*/
	public void setMyStatus(CurrentPlayerStatus proposedStatus) {
		if (proposedStatus.getPriorityLevel() >= myStatus.getPriorityLevel()){
			this.myStatus = proposedStatus;
		}
		return;
	}
	
	/**
	 * Set as disconnected, bypassing the priority level
	 */
	public void disconnected() {
		this.myStatus = CurrentPlayerStatus.DISCONNECTED;
	}
	
	public CurrentPlayerStatus getMyStatus() {
		return myStatus;
	}

	
}
