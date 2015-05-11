package it.escape.server.model.game.character;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.model.game.gamemap.StartingCell;

public class Alien extends Character {

	
	
	public Alien(StartingCell start) {
		myCell = start;
		maxDistance = 2;
		team = PlayerTeams.ALIENS;
	}
	


	public void attack() {

		
	}
	
	
	


}
