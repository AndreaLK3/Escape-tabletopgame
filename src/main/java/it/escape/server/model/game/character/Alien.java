package it.escape.server.model.game.character;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.model.game.gamemap.StartingCell;

public class Alien extends Character {

	
	
	public Alien(StartingCell start) {
		myCell = start;
		this.team = PlayerTeams.ALIENS;
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
