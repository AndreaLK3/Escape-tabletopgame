package it.escape.server.model.game.cards;

public class TempMain {
	
	public void main (String args[]) {
		
		Deck sectorDeck, escapeDeck;
		Card temp;
		
		System.out.println ("Test main for the decks of cards");
		System.out.println ("Creating a new SectorDeck and a new EscapeDeck");
		sectorDeck = new SectorDeck();
		escapeDeck = new EscapeDeck();
		
		System.out.println("Performing operations on EscapeDeck:");
		System.out.println("Drawing several cards");
		for (int i = 0; i<5 ; i++)
		{	temp = escapeDeck.drawCard();
			System.out.println (temp.toString());}
		
		
	}

}
