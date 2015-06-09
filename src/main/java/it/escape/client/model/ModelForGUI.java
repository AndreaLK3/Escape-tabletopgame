package it.escape.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ModelForGUI extends Observable {
	
	private MyPlayerState myPlayerState;
	
	private List<PlayerState> playerStates;

	public ModelForGUI() {
		super();
		playerStates = new ArrayList<PlayerState>();
		
	}

	public MyPlayerState getMyPlayerState() {
		return myPlayerState;
	}

	public List<PlayerState> getPlayerStates() {
		return playerStates;
	}
	
	public PlayerState getSpecificPlayerState(String name) {
		for (PlayerState s : playerStates) {
			if (s.getMyName().equals(name)) {
				return s;
			}
		}
		return null;
	}
	
	/**
	 * Update a player's status, adds a new player to the list if needed
	 * The caller must call finishedUpdating() himself after doing this
	 * @param name
	 * @param newStatus
	 */
	public void updatePlayerStatus(String name, CurrentPlayerStatus newStatus) {
		PlayerState existing = getSpecificPlayerState(name);
		if (existing == null) {
			PlayerState newEntry = new PlayerState(name, newStatus);
			playerStates.add(newEntry);
		} else {
			existing.setMyStatus(newStatus);
		}
	}
	
	/**
	 * Update a player's noise location, adds a new player to the list if needed
	 * The caller must call finishedUpdating() himself after doing this
	 * @param name
	 * @param newStatus
	 */
	public void updatePlayerStatus(String name, String noiseLocation) {
		PlayerState existing = getSpecificPlayerState(name);
		if (existing == null) {
			PlayerState newEntry = new PlayerState(name, noiseLocation);
			playerStates.add(newEntry);
		} else {
			existing.setLastNoiseLocation(noiseLocation);
		}
	}
	
	/**
	 * The controller should access model data using the getters,
	 * modify said data, and only then call finishedUpdating()
	 * which will trigger the observer notify mechanism
	 */
	public void finishedUpdating() {
		setChanged();
		notifyObservers();
	}
}
