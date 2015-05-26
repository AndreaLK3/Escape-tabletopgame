package it.escape.server.model.game.cards;

import it.escape.server.model.game.cards.objectcards.AttackCard;
import it.escape.server.model.game.cards.objectcards.SedativesCard;

public class ObjectDeck extends Deck {
	
	/** Symbolic constants define the number of cards of each kind in this deck*/
	private final static int ATTACK = 5;
	private final static int SEDATIVES = 5;
	
	public ObjectDeck() {
		super();
		int i;
		
		for (i=0; i<ATTACK; i++)
		{	theDeck.add(new AttackCard());
			
		}
		for (i=0; i<SEDATIVES; i++)
		{	theDeck.add(new SedativesCard());
			
		}
		
		this.shuffleDeck();
	}
}
