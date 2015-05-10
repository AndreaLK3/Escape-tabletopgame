package it.escape.server.model.game.character;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.cards.Deck;
import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.cards.SectorDeck;
import it.escape.server.model.game.exceptions.CellNotExistsException;
import it.escape.server.model.game.exceptions.MovementOutOfRangeException;
import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

/**This class defines the behaviour for a generic Character; 
 * it encompasses the functions that are used by both Aliens and Humans
 * @author andrea
 */
public class Character {
	
	protected PlayerTeams team;
	protected Cell myCell;
	protected int maxDistance;
	protected Card aCard;
	
	public void drawCard(Deck aDeck) {
		aCard = aDeck.drawCard();
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
	 * In case the movement to the proposed cell is not allowed, this method throws (and catches) 2 exceptions:
	 * -CellNotExistsException, if the proposed cell does not exist in the game map
	 * -MovementOutOfRangeException, if the character can not move to that cell because it is too distant
	 * @param proposedCell
	 * @return true, if movement is allowed; otherwise, false 
	 */
	public boolean canMove(Cell proposedCell) {
		if (proposedCell==null)	//la cella è passata dall'utente... questo in verità significa che la cella non esiste sulla mappa
				try {			//(forse è meglio che il controllo sull'esistenza della cella lo faccia la mappa?)
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
