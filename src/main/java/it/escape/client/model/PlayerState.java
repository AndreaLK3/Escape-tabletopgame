package it.escape.client.model;

import it.escape.strings.StringRes;

/**This class in client.model defines:
 * Name, Status, LastNoiseLocation of a Player.
 * It is updated by the UpdaterSwing in the Controller, 
 * according to the messages the Server sends.
 */
public class PlayerState {
	
	protected String myName;
	
	protected CurrentPlayerStatus myStatus;
	
	protected String lastNoiseLocation;
	
	// value to initialize lastNoiseLocation to
	protected static final String noNoise = StringRes.getString("client.applogic.unknownCoordinates");

	public PlayerState() {
		this.myName = "Unknown";
		myStatus = CurrentPlayerStatus.DISCONNECTED;
		lastNoiseLocation = noNoise;
	}
	
	public PlayerState(String myName) {
		setMyName(myName);
		myStatus = CurrentPlayerStatus.DISCONNECTED;
		lastNoiseLocation = noNoise;
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


	public void setMyStatus(CurrentPlayerStatus myStatus) {
		this.myStatus = myStatus;
	}
	
	public CurrentPlayerStatus getMyStatus() {
		return myStatus;
	}

	
}
