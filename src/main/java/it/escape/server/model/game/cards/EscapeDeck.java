package it.escape.server.model.game.cards;

import it.escape.server.model.game.cards.escapecard.EscapeCard;
import it.escape.server.model.game.cards.escapecard.EscapeCardColor;

public class EscapeDeck extends Deck {
	/** Symbolic constants define the number of cards of each kind in this deck*/
	private final static int REDSHUTTLE = 3;
	private final static int GREENSHUTTLE = 3;
	
	public EscapeDeck() {
		super();
		int i;
		
		
		for (i=0; i<REDSHUTTLE; i++)
		{	theDeck.add(new EscapeCard(EscapeCardColor.RED));
			
		}
		for (i=0; i<GREENSHUTTLE; i++)
		{	theDeck.add(new EscapeCard(EscapeCardColor.GREEN));
			
		}
	
		this.shuffleDeck();
	}
}

