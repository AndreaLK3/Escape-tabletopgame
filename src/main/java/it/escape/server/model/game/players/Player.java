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

	public void addCard(ObjectCard card) {
		myHand.addCard(card);
	}
	
	/**
	 * extract and return a card from the player's hand.
	 * a card matching "key" is selected
	 * If no valid card is found, the method will return null
	 * @param key
	 * @return
	 */
	public ObjectCard drawCard(String key) {
		// match key with one or none of the cards in the player's hand
		// remove said card from the hand
		// return the card, null if not found
		return null;
	}
	
}
