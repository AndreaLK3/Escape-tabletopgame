package it.escape.server.model.game.character;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.cards.Deck;
import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.cards.SectorDeck;
import it.escape.server.model.game.exceptions.CellNotExistsException;
import it.escape.server.model.game.exceptions.MovementOutOfRangeException;
import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.model.game.gamemap.StartingCell;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.supergame.SuperGame;

/**This class defines the behaviour for a generic Character; 
 * it encompasses the functions that are used by both Aliens and Humans.
 * The constructors are in the subclasses.
 * @author andrea
 */
public class Character implements CellAction, CardAction {
	
	private SuperGame supervisor;
	protected Cell myCell;
	protected int maxDistance;
	protected Card aCard;
	protected PlayerTeams team;
	protected DecksHandler decksRef;
	
	
	/**
	 * constructor implementing the operations which are common among all characters
	 * @param start
	 * @param decksRef
	 */
	public Character(StartingCell start, DecksHandler decksRef) {
		this.myCell = start;
		this.decksRef = decksRef;
	}
	
	
	public void drawSectorCard() {
		drawCard(decksRef.getsDeck());
		aCard.effect(this);
	}
	public void drawObjectCard() {
		drawCard(decksRef.getoDeck());
		aCard.effect(this);
	}
	
	/** draw a card from a deck passed as parameter
	 * @param aDeck
	 */
	private void drawCard(Deck aDeck) {
		aCard = aDeck.drawCard();
	}
	
	/**Checks if the proposed movement does not exceeds maximum range
	 * @param proposedCell
	 * @return true, if movement is in range; otherwise, false 
	 */
	public boolean canMove(Cell proposedCell) {
		if ((myCell.getPosition().distanceFrom(proposedCell.getPosition()) > maxDistance)){
			return false;
			}
		else
			return true;
	}
	
	/**Performs a (possibly multi-step) movement to a destination
	 * returns true on success
	 * @param proposedCell
	 */
	public boolean move(Cell proposedCell){
		if (!canMove(proposedCell)) {
			return false;
		}
		else {
			myCell = proposedCell;
			return true;
		}
	}
	
	/**
	 * moves to a cell and triggers whatever action the cell implements
	 * @param proposedCell
	 * @return
	 */
	public boolean actionAfterMove(Cell proposedCell) {
		if (!move(proposedCell)) {
			return false;
		}
		myCell.doAction(this);
		return true;
	}
	
	
	
	public void attack(){}
	
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

	// CellAction methods
	
	public void escape() {
		
	}

	public void noAction() {
	
	}
	
	

	// CardAction methods
	
	public void noiseInMySector() {
		
	}

	public void noiseInOtherSector() {
		
	}

	public void declareSilence() {
	
		
	}

}
