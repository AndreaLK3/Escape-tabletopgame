package it.escape.server.model.game.players;

import it.escape.server.model.game.cards.ObjectCard;

import java.util.ArrayList;
import java.util.List;

/**This class handles a Hand of Cards (in this case, it contains ObjectCards)
 * It has a maximum number of cards it can hold (here, 3)
 * @author andrea
 */
public class Hand {
	
	public final static int MAXOBJECTS = 3;
	
	private List<ObjectCard> handOfCards;
	
	
	public List<ObjectCard> getHandOfCards() {
		return new ArrayList<ObjectCard>(handOfCards);
	}

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
		if (!isEmpty()) {
			handOfCards.remove(card);
		}
	}
	
	/**Returns the name of i-th card in the list;
	 * this is required when the User has to discard 1 card because
	 * the hand has 4 cards, and doesn't answer the request. 
	 * In the UMR the default choice, used by the timeout override, is the String returned by getCardName(1)*/
	public String getCardName(int i) {
		if (!isEmpty()) {
			String nameCard = handOfCards.get(i).getClass().getSimpleName();
			String name = nameCard.substring(0, nameCard.length()-4);
			return name.toLowerCase();
		}
		else
			return null;
	}
	
	/**Returns an array with all the names of the currently owned cards*/
	public String[] getAllCardNames() {
		String [] cardNames = new String[handOfCards.size()];
		for(int i=0; i<handOfCards.size() ; i++) {
			cardNames[i] = getCardName(i);
		}
		return cardNames;
	}
	
	/**Organizes the names of the owned cards, returned by getAllCardNames, as a printable String*/
	public String getAllCardNamesAsString() {
		String cards="";
		String[] cardNames = getAllCardNames();
		
		for (int i=0; i< handOfCards.size() ; i++) {
			cards = cards + cardNames[i] + " - ";
		};
		return cards;
	}
	
	/** 
	 * Removes the card that is located at the i-th position in the list	 */
	public void removeCard(int i) {
		if (!isEmpty()) {
			handOfCards.remove(i);
		}
	}
	
	public boolean containsCard(ObjectCard card) {
		return handOfCards.contains(card);
	}
	
	/**
	 * attempt to retrieve a card from the player's hand, if no card is found,
	 * null is returned.
	 * The provided key will be matched against the objectcards' actual
	 * dynamic class name (i.e. "AttackCard"). The match is case-insensitive,
	 * and keys matching only the beginning of the class name are also accepted.
	 * @param s
	 * @return
	 */
	public ObjectCard getCardFromString(String s) {
		String key = s.toLowerCase();
		for (ObjectCard card : handOfCards) {
			String name = card.getClass().getSimpleName(); // dynamic class name (i.e. "SedativesCard")
			if (name.toLowerCase().startsWith(key)) { 
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
