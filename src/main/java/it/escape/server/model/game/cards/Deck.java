package it.escape.server.model.game.cards;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/** This class defines the behaviour of a deck of cards; 
 * The data structure is an ArrayList of Card objects;
 * there is also a int counter variable, that keeps track of the number of cards left in the deck;
 * objects are not destroyed and recreated when cards are drawn; instead, the counter variable is decremented.
 * It is extended by SectorDeck, ObjectDeck, and EscapeDeck; those classes use their own constructors.
 * Note: When the decks are created, they are already shuffled.
 * @author andrea
 */
public class Deck {
	

	protected List<Card> theDeck = new ArrayList<Card>();
	protected int counter;
	private Card temp;
	protected static Random randGen = new Random();
	
	/**This function puts all the cards back in the deck, and for as many times as the number of cards
	 * takes one card at random and places it at the bottom of the deck*/
	public void shuffleDeck(){
		
		counter = theDeck.size();		
		
		for (int i=0; i<counter; i++)
		{	temp = theDeck.remove(randGen.nextInt(counter)); 
			theDeck.add(temp);}
		
	}

	/**
	 * The function removes the first card from the ArrayList theDeck, and returns it
	 * @return theDeck.get(counter-1)
	 */
	public Card drawCard() {
		if (!theDeck.isEmpty())
			{temp= theDeck.get(counter-1);
			counter--;
			return temp;}
		else 
		{	System.out.println ("This deck of cards is empty! It must be reshuffled anew");
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

	
		

}
