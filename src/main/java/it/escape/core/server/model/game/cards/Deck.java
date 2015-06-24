package it.escape.core.server.model.game.cards;
import it.escape.tools.utils.LogHelper;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

/** This class defines the behaviour of a deck of cards; 
 * The data structure is an ArrayList of Card objects;
 * there is also a int counter variable, that keeps track of the number of cards left in the deck;
 * objects are not destroyed and recreated when cards are drawn; instead, the counter variable is decremented.
 * It is extended by SectorDeck, ObjectDeck, and EscapeDeck; those classes use their own constructors.
 * Note: When the decks are created, they are already shuffled.
 * @author andrea
 */
public class Deck {
	protected static final Logger LOGGER = Logger.getLogger( Deck.class.getName() );

	protected List<Card> theDeck = new ArrayList<Card>();
	private int counter;
	private Card temp;
	protected static Random randGen = new Random();
	
	/**This function puts all the cards back in the deck, and for as many times as the number of cards
	 * takes one card at random and places it at the bottom of the deck*/
	public void shuffleDeck(){
		
		counter = theDeck.size();		
		
		for (int i=0; i<counter; i++)
		{	temp = theDeck.remove(randGen.nextInt(counter)); 
			theDeck.add(temp);}
		return;
	}

	/**
	 * The function removes the first card from the ArrayList theDeck, and returns it
	 * If the deck is empty, it @throws ArrayIndexOutfBoundsException and catches it, calling the function once again
	 * @return theDeck.get(counter-1), and then counter--
	 */
	public Card drawCard() {
		try
			{temp= theDeck.get(counter-1);
			counter--;
			return temp;}
		catch (ArrayIndexOutOfBoundsException var1) {
			LOGGER.finest("This deck of cards is empty! It must be reshuffled anew" + var1.getMessage());
			this.shuffleDeck();
			return this.drawCard();
		}
	}
	
	
	public int size() {
		return counter;
	}
	
	public boolean isEmpty() {
		return (counter==0);
	}

	public Deck() {
		LogHelper.setDefaultOptions(LOGGER);
	}

	/**
	 * for testing purposes
	 * @return
	 */
	public List<Card> getTheDeck() {
		return theDeck;
	}
	
	
}
