package it.escape.server.model.game.cards;
import java.util.List;
import java.util.ArrayList;

public class Deck {
	
	private List<Card> theDeck = new ArrayList<Card>();
	
	public Deck(DeckType type) {
		if (type == DeckType.SECTOR)
		{
		}
		if (type == DeckType.ESCAPE)
		{
			
		}
		if (type == DeckType.OBJECT)
		{
			
		}
	}
	
	public void shuffleDeck(){
		
	}

	public Card drawCard() {
		return null;
		
	}
	
	public void isEmpty() {
		
		
	}
}
