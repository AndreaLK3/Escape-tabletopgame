package it.escape.server.model.game.cards;

import it.escape.server.model.game.cards.objectCards.ObjectCard;
import java.util.List;
import java.util.ArrayList;

/**This class handles a Hand of Cards (in this case, it contains ObjectCards)
 * It has a maximum number of cards it can hold (here, 3)
 * @author andrea
 */
public class Hand {
	
	public final static int MAXOBJECTS = 3;
	
	List<ObjectCard> handOfCards;
	
	/**Constructor**/
	public Hand() {
		handOfCards = new ArrayList<ObjectCard>();
	}
	
	public void addCard(ObjectCard c) {
		handOfCards.add(c);
		
	}

	/** 
	 * Removes the card that is located at the i-th position in the list	 */
	private void removeCard(int i) {
		if (!isEmpty())
			handOfCards.remove(i);
	}

	public boolean containsCard(String cardName) {
		return false;
	}
	
	public boolean isEmpty() {
		return handOfCards.isEmpty();
	}
	
	public boolean isFull() {
		return handOfCards.size() >= MAXOBJECTS;
	}
	
	public boolean isOverFull() {
		return handOfCards.size() > MAXOBJECTS;
	}
}
