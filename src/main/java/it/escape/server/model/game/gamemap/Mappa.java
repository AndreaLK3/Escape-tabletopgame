package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.gamemap.positioning.PositionCubic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mappa {
	
	private List characters;
	
	private HashMap<String, Cell> cells;

	public Mappa() {
		characters = new ArrayList<Character>();
		cells = new HashMap<String,Cell>();
	}
	
	public void moveCharacter(Character character, PositionCubic dest) {
		// To be implemented
	}
	
	public void noiseInSector(Cell sector) {
		// To be implemented
	}
}
