package it.escape.server.model.game.character;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.cards.Deck;
import it.escape.server.model.game.gamemap.Cell;

public class Character {
	
	protected PlayerTeams team;
	protected Cell myCell;
	protected int maxDistance;
	
	public Card drawCard(/*Deck aDeck*/) {
		return null;
	}
	
	public void move(){	
	}
	
	public void attack(){}
	
	public void escape(){}
	
	public void noAction(){}
	
	public Cell getMyCell() {
		return myCell;
	}

	public int getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(int maxDistance) {
		this.maxDistance = maxDistance;
	}

	public PlayerTeams getTeam() {
		return team;
	}
}
