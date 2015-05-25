package it.escape.server.model.game.players;

import it.escape.server.model.game.cards.Hand;
import it.escape.server.model.game.cards.ObjectCard;
import it.escape.server.model.game.exceptions.CardNotPresentException;
import it.escape.utils.LogHelper;

import java.util.logging.Logger;



public abstract class Player {
	
	protected static final Logger log = Logger.getLogger( Player.class.getName() );
	
	protected int maxRange;
	protected boolean hasMoved;
	protected boolean alive;
	protected Hand myHand;
	
	
	public Player () {
		LogHelper.setDefaultOptions(log);
		alive = true;
	}
	

	public void startTurn() {
		hasMoved = false;
	}


	public int getMaxRange() {
		return maxRange;
	};

	public void setMaxRange(int newRange) {
		maxRange = newRange;
	};
	
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * differently implemented in aliens and humans (humans can defend)
	 */
	public abstract void die();

		
	public boolean HasMoved() {
		return hasMoved;
	}


	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public abstract PlayerTeams getTeam();

	public Hand getMyHand() {
		return myHand;
	}

	public void addCard(ObjectCard card) {
		myHand.addCard(card);
	}
	
	/**
	 * extract and return a card from the player's hand
	 * a card matching "key" is selected.
	 * The card is removed from the player's hand
	 * If no valid card is found, the method will return null.
	 * @param key
	 * @return
	 * @throws CardNotPresentException 
	 */
	public ObjectCard drawCard(String key) throws CardNotPresentException {
		ObjectCard theCard;
		
		// match key with one or none of the cards in the player's hand
		theCard = myHand.getCardFromString(key);
		if (theCard == null) {  // not found
			throw new CardNotPresentException();
		}
		
		// remove said card from the hand
		myHand.removeCard(theCard);
		return theCard;
		
	}

	public void setEscaped(){};
	
}
