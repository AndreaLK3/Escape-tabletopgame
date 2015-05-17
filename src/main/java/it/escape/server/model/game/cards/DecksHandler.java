package it.escape.server.model.game.cards;

public class DecksHandler {
	
	private EscapeDeck eDeck;
	private SectorDeck sDeck;
	private ObjectDeck oDeck;
	
	private static DecksHandler decksHandlerInstance;
	
	public static DecksHandler getDecksHandler() {
		if (decksHandlerInstance == null)
			decksHandlerInstance = new DecksHandler();
		return decksHandlerInstance;
	}
	
	private DecksHandler() {
		eDeck = new EscapeDeck();
		sDeck = new SectorDeck();
		oDeck = new ObjectDeck();
	}

	public Card drawSectorCard() {
		return sDeck.drawCard();
	}

	public Card drawObjectCard() {
		return oDeck.drawCard();
	}

	public Card drawEscapeCard() {
		return eDeck.drawCard();
	}

}