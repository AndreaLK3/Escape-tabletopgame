package it.escape.server.model.game.character;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.cards.Deck;
import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.cards.SectorDeck;
import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public class Character {
	
	protected PlayerTeams team;
	protected Cell myCell;
	protected int maxDistance;
	protected DecksHandler theDecks;
	
	public Card drawCard(Deck aDeck) {
		return null;
	}
	
	public void move(Cell proposedCell){
		if (!canMove(proposedCell))	
			return;
		else
		{	myCell = proposedCell;
			return;
		}
			
	}
	
	/**
	 * Possiamo introdurre 2 eccezioni diverse, una CellNotExists e un' altra OutOfRange
	 * @param proposedCell
	 * @return
	 */
	public boolean canMove(Cell proposedCell) {
		if (proposedCell==null || (myCell.getPosition().distanceFrom(proposedCell.getPosition()) > maxDistance)) 
				return false;
		else
			return true;
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
