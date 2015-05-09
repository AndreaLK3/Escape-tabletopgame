package it.escape.server.model.game.character;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.model.game.gamemap.StartingCell;

public class Human extends Character {

	
	// private Hand manoDiCarteOggetto
	
	
	public Human(StartingCell start) {
		myCell = start;
		maxDistance = 1;
		this.team = PlayerTeams.HUMANS;
	}
	
	public void move() {
	
		
	}

	public void attack() {
	
		
	}
	public Card drawCard() {
		
		return null;
	}

	public void escape() {
		

	}

		
	

}
