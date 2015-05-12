package it.escape.server.model.game.character;

import java.util.List;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.cards.ObjectCard;
import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.model.game.gamemap.StartingCell;

public class Human extends Character {

	
	private List<ObjectCard> handOfObjects;
	private final static int MAXOBJECTS = 3;
	
	public Human(StartingCell start, DecksHandler decksRef) {
		super(start, decksRef);
		maxDistance = 1;
		team = PlayerTeams.HUMANS;
	}
	
	public void attack() {

		
	}
	
	
	public void escape() {
		

	}

		
	

}
