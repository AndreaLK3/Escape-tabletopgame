package it.escape.server.model.game.players;

import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.model.game.cards.ObjectCard;
import it.escape.server.model.game.exceptions.CardNotPresentException;
import it.escape.utils.LogHelper;

import java.util.logging.Logger;



public abstract class Player implements PlayerActionInterface {
	
	protected static final Logger log = Logger.getLogger( Player.class.getName() );
	protected boolean userIdle;
	protected int maxRange;
	protected boolean hasMoved;
	protected boolean hasAttacked;
	protected boolean alive;
	protected Hand myHand;
	
	protected String name;
	
	
	public Player (String name) {
		LogHelper.setDefaultOptions(log);
		this.name = name;
		alive = true;
		myHand = new Hand();
		userIdle=false;
	}
	
	public void changeName(String newname) {
		name = newname;
	}

	public void startTurn() {
		hasMoved = false;
		hasAttacked = false;
	}

	public String getName() {
		return name;
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
	
	public boolean hasCards() {
		return !myHand.isEmpty();
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
		
		theCard = searchForCard(key);
		
		// remove said card from the hand
		myHand.removeCard(theCard);
		return theCard;
		
	}
	
	public ObjectCard searchForCard(String key) throws CardNotPresentException{
		ObjectCard theCard;
		// match key with one or none of the cards in the player's hand
		theCard = myHand.getCardFromString(key);
		if (theCard == null) {  // not found
			throw new CardNotPresentException();
		}
		return theCard;
		
	}

	public void setEscaped(){}


	public void setHasAttacked() {
		hasAttacked = true;
	};
	
	public boolean hasAttacked() {
		return hasAttacked;
	};
	
	public boolean isUserIdle() {
		return userIdle;
	}


	public void setUserIdle(boolean userIdle) {
		this.userIdle = userIdle;
	}
}
