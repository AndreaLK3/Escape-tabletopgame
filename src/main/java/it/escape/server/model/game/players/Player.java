package it.escape.server.model.game.players;

import it.escape.server.model.game.cards.objectCards.ObjectCard;

import java.util.List;



public abstract class Player {
	
	protected int maxRange;
	protected boolean hasMoved;
	protected boolean alive;
	
	protected final static int MAX_CARDS = 3;

	protected List<ObjectCard> handOfCards;
	
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
	
	public boolean getHasMoved() {
		return hasMoved;
	}
	
	/**
	 * Add an object card to the player's hand, return false if the player
	 * cannot take any more cards
	 * @param card
	 * @return
	 */
	public boolean acquireCard(ObjectCard card) {
		if (handOfCards.size() > MAX_CARDS) {
			return false;
		}
		else {
			handOfCards.add(card);
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
	
	/**
	 * differently implemented in aliens and humans (humans can defend)
	 */
	public abstract void die();

	/**
	 * clean up sedatives / adrenaline effect (in the human)
	 */
	public abstract void endOfTurn();
	
	
	public abstract PlayerTeams getTeam();


	
}
