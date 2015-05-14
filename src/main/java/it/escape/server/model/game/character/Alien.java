package it.escape.server.model.game.character;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.model.game.gamemap.StartingCell;

public class Alien extends GameCharacter {

	private final static int ALIENRANGE=2;
	
	public Alien(StartingCell start, DecksHandler decksRef) {
		super(start);
		maxDistance = ALIENRANGE;
		team = PlayerTeams.ALIENS;
	}
	

	
	
	


}
