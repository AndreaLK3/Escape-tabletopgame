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
	
	public Hand() {
		handOfCards = new ArrayList<ObjectCard>();
	}
	
	
	
	public boolean isEmpty() {
		return handOfCards.isEmpty();
	}
	
	public boolean isFull() {
		return handOfCards.size() == MAXOBJECTS;
	}
	
	public void addCard(ObjectCard c) {
		handOfCards.add(c);
		
	}

	public void removeCard(int i) {
		handOfCards.remove(i);
	}
}
