package it.escape.server.model.game.cards;

public class DecksHandler {
	
	private EscapeDeck eDeck;
	private SectorDeck sDeck;
	private ObjectDeck oDeck;
	
	public DecksHandler() {
		eDeck = new EscapeDeck();
		sDeck = new SectorDeck();
		oDeck = new ObjectDeck();
	}

	public EscapeDeck geteDeck() {
		return eDeck;
	}

	public SectorDeck getsDeck() {
		return sDeck;
	}

	public ObjectDeck getoDeck() {
		return oDeck;
	}

}
