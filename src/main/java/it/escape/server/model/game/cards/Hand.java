package it.escape.server.model.game.cards;

import it.escape.server.model.game.cards.objectCards.AttackCard;
import it.escape.server.model.game.cards.objectCards.ObjectCard;
import it.escape.server.model.game.cards.objectCards.SedativesCard;

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
	 * Removes the card of the specified kind	n:we have to see if it works */
	public void removeCard(ObjectCard card) {
		if (!isEmpty())
			handOfCards.remove(card);
	}
	
	/** 
	 * Removes the card that is located at the i-th position in the list	 */
	public void removeCard(int i) {
		if (!isEmpty())
			handOfCards.remove(i);
	}
	
	public boolean containsCard(ObjectCard card) {
		return handOfCards.contains(card);
	}

	public ObjectCard getCardFromString(String s) {
		for (ObjectCard card : handOfCards) {
			String name = card.getClass().getSimpleName(); // dynamic class name (i.e. "SedativesCard")
			if (name.toLowerCase().startsWith(s.toLowerCase())) {
				return card;
			}
		}
		return null;
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
