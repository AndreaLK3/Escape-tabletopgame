package it.escape.server.model.game;

import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.Player;

import java.util.Observable;

/**
 * Announcer class: simple observable, relays a message to the observers;
 * those are located in the server view (our net-communication threads).
 * Each running game will instance only one announcer (it is a singleton)
 * @author michele
 */
public class Announcer extends Observable {
	
	private String message;
	
	private static Announcer instance = null;
	
	public static Announcer createAnnouncerInstance() {
		if (instance == null) {
			instance = new Announcer();
		}
		return instance;
	}
	
	public static Announcer getAnnouncerInstance() {
		return instance;
	}
	
	private Announcer() {
		
	}
	
	public void announceAttack(Player player, PositionCubic position) {
		// encode player and position to a string
		// use announce to send that string
	}
	
	public void announce(String message) {
		this.message = message;
		setChanged();
		notifyObservers();
	}

	public String getMessage() {
		return message;
	}
}
