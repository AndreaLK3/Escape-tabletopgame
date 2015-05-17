package it.escape.server.model.game.cards;

import it.escape.utils.LogHelper;

import java.util.logging.Logger;

public class CardsMain {
	
	protected static final Logger log = Logger.getLogger( CardsMain.class.getName() );
	
	public static void main (String args[]) {
		LogHelper.setDefaultOptions(log);
		Deck escapeDeck;
		Card temp;
		
		log.fine("Test main for the decks of cards");
		log.fine("Creating a new SectorDeck and a new EscapeDeck");
		escapeDeck = new EscapeDeck();
		
		log.fine("Performing operations on EscapeDeck:");
		log.fine("Drawing several cards");
		for (int i = 0; i<11 ; i++) {
			temp = escapeDeck.drawCard();
			log.finer(temp.toString());
			log.finer("Determining the number of remaining cards in the deck:");
			log.finer(String.valueOf(escapeDeck.size()));
			}
		
		
		
	}

}
