package it.escape.core.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ModelForGUI extends Observable {
	
	private MyPlayerState myPlayerState;
	
	private List<PlayerState> playerStates;
	
	private PlayerState nowPlaying;
	
	private GameStatus gameStatus;
	
	private VictoryState victoryState;
	
	private int turnNumber;

	public ModelForGUI() {
		super();
		playerStates = new ArrayList<PlayerState>();
		myPlayerState = new MyPlayerState();
		victoryState = new VictoryState();
		nowPlaying = null;
		setTurnNumber(0);
		setGameStatus(GameStatus.WAITING_FOR_PLAYERS);
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
	 * Points nowPlaying to a named playerState,
	 * adding it if it does not exist
	 * @param name
	 */
	public void updateNowPlaying(String name) {
		updatePlayerExists(name);
		nowPlaying = getSpecificPlayerState(name);
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
	 * Update a player's name, adds a new player to the list if needed
	 * The caller must call finishedUpdating() himself after doing this
	 * @param name
	 * @param newStatus
	 */
	public void updatePlayerRename(String name, String newName) {
		PlayerState existing = getSpecificPlayerState(name);
		if (existing == null) {
			PlayerState newEntry = new PlayerState(newName);
			playerStates.add(newEntry);
		} else {
			existing.setMyName(newName);
		}
	}
	
	/**
	 * Adds a new player to the list if needed, otherwise don't touch it
	 * The caller must call finishedUpdating() himself after doing this
	 * @param name
	 * @param newStatus
	 */
	public void updatePlayerExists(String name) {
		PlayerState existing = getSpecificPlayerState(name);
		if (existing == null) {
			PlayerState newEntry = new PlayerState(name);
			playerStates.add(newEntry);
		}
	}
	
	/**
	 * To be called after the end of the game.
	 * Update the player statuses so that they reflect
	 * the data stored in victoryStatus 	 */
	public void finalRefreshPlayerStatus() {
		List<String> alienWinners = victoryState.getAlienWinners();
		List<String> humanWinners = victoryState.getHumanWinners();
		for (PlayerState p : playerStates) {
			if (alienWinners.contains(p.getMyName()) || humanWinners.contains(p.getMyName())) {
				p.setMyStatus(CurrentPlayerStatus.WINNER);
			} else {
				p.setMyStatus(CurrentPlayerStatus.LOSER);
			}
		}
	}
	
	/**
	 * The controller should access model data using the getters,
	 * modify said data using the setters, and only then call finishedUpdating()
	 * which will trigger the observer notify mechanism
	 */
	public void finishedUpdating() {
		setChanged();
		notifyObservers();
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public PlayerState getNowPlaying() {
		return nowPlaying;
	}

	public VictoryState getVictoryState() {
		return victoryState;
	}
	
}
