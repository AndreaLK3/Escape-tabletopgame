package it.escape.server.model.game.cards;

public class EscapeDeck extends Deck {
	/** Symbolic constants define the number of cards of each kind in this deck*/
	private final static int REDSHUTTLE = 5;
	private final static int GREENSHUTTLE = 5;
	
	public EscapeDeck() {
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

