package it.escape.server.model.game.players;

import it.escape.server.model.game.cards.objectCards.ObjectCard;
import it.escape.server.model.game.cards.Hand;



public abstract class Player {
	
	protected int maxRange;
	protected boolean hasMoved;
	protected boolean alive;
	protected Hand myHand;
	
	
	public Player () {
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

	/**
	 * It is implemented in the Human class, to clean up effects of Sedatives/Adrenaline
	 */
	public abstract void endOfTurn();
	
	
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

	/**
	 * Add an object card to the player's hand, return false if the player
	 * cannot take any more cards
	 * @param card
	 * @return
	 */
	public boolean acquireCard(ObjectCard card) {
		if (myHand.isEmpty()) {
			return false;
		}
		else {
			myHand.addCard(card);
			return true;
		}
	}
	
	/**
	 * extract and return a card from the player's hand.
	 * a card matching "key" is selected
	 * If no valid card is found, the method will return null
	 * @param key
	 * @return
	 */
	public ObjectCard drawCard(String key) {
		// match key with one or none of the card in the player's hand
		// remove said card from the hand
		// return the card, null if not found
		return null;
	}
	
}
