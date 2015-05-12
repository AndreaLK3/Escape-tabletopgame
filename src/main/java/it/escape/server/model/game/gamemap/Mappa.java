package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mappa {
	
	private List characters;
	
	private HashMap<String, Cell> cells;
	
	private DecksHandler decks;

	public Mappa() {
		characters = new ArrayList<Character>();
		cells = new HashMap<String,Cell>();
		decks = new DecksHandler();
	}
	
	public void moveCharacter(Character character, PositionCubic dest) {
		// To be implemented
	}
	
	public void noiseInSector(Cell sector) {
		// To be implemented
	}
}
