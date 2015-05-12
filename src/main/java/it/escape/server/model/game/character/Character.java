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

/**This class defines the behaviour for a generic Character; 
 * it encompasses the functions that are used by both Aliens and Humans.
 * The constructors are in the subclasses.
 * @author andrea
 */
public class Character implements CellAction, CardAction {
	
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
	
	/**
	 * draw a card from a deck passed as parameter
	 * @param aDeck
	 */
	private void drawCard(Deck aDeck) {
		aCard = aDeck.drawCard();
	}
	
	/**
	 * perform a (possibly multi-step) movement to a destination
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
	public boolean moveAndLand(Cell proposedCell) {
		if (!move(proposedCell)) {
			return false;
		}
		myCell.doAction(this);
		return true;
	}
	
	/**
	 * In case the movement to the proposed cell is not allowed, this method throws (and catches) 2 exceptions:
	 * -CellNotExistsException, if the proposed cell does not exist in the game map
	 * -MovementOutOfRangeException, if the character can not move to that cell because it is too distant
	 * @param proposedCell
	 * @return true, if movement is allowed; otherwise, false 
	 */
	public boolean canMove(Cell proposedCell) {
		if (proposedCell==null)	
				try {			
					throw new CellNotExistsException();
				}
				catch (CellNotExistsException var1) {
					return false;
				}
		if ((myCell.getPosition().distanceFrom(proposedCell.getPosition()) > maxDistance)) 
			try {
				throw new MovementOutOfRangeException();
			}
			catch (MovementOutOfRangeException var1) {
				return false;
			}
		else
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

	// AzioneCella methods
	
	public void escape() {
		
	}

	public void noAction() {
	
	}
	
	public void drawSectorCard() {
		drawCard(decksRef.getsDeck());
		aCard.effect(this);
	}

	// AzioneCarta methods
	
	public void noiseInMySector() {
		// TODO Auto-generated method stub
	}

	public void noiseInOtherSector() {
		// TODO Auto-generated method stub
	}

	public void declareSilence() {
		// TODO Auto-generated method stub
		
	}

}
