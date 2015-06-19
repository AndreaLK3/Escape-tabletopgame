package it.escape.core.server.model.game.players;

import it.escape.core.server.controller.game.actions.PlayerActionInterface;
import it.escape.core.server.model.game.cards.ObjectCard;
import it.escape.core.server.model.game.exceptions.CardNotPresentException;
import it.escape.tools.utils.LogHelper;

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
	

	/**This method checks if the Player has playable cards in his hand
	 * It is invoked by the TurnHandlerHuman to decide if we have to ask the User to play an ObjectCard or not.
	 * It also check the hasMoved variable of the Player to know which cards to look for.
	 * @return boolean */
	public boolean hasPlayableCards() {
		String playableBeforeMoveCardNames[] = {"teleport", "lights", "sedatives","adrenaline"};
		String playableAfterMoveCardNames[] = {"teleport", "lights"};
		
		if (myHand.isEmpty()) {
			return false;
		}
		else {
			if (!hasMoved) {                                                 //if the player has not moved yet
				for (int i=0; i<playableBeforeMoveCardNames.length ; i++) {	      //for each of the playable cards' names
					if (myHand.getCardFromString(playableBeforeMoveCardNames[i])!=null) {  //if there is a corresponding card
						return true;
					}
				}
			}
			else {                                                             //if the player has already moved
				for (int i=0; i<playableAfterMoveCardNames.length ; i++) {			//for each of the playable cards' names
					if (myHand.getCardFromString(playableAfterMoveCardNames[i])!=null) {   //if there is a corresponding card
						return true;
					}
				}
			}
		}
		return false;
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
	
	/**This method goes on to call the method of class hand getCardFromString(String key).
	 * It doesn't modify anything.
	 * @returns the Card we were looking for, or null if not present*/
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
