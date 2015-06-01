package it.escape.server.model.game.cards;


/**This class initializes the decks, which contain different kinds of cards, and makes them visible.
 * n: It is a singleton.
 * @author andrea
 */
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
	
	public DecksHandler() {
		eDeck = new EscapeDeck();
		sDeck = new SectorDeck();
		oDeck = new ObjectDeck();
	}

	public SectorCard drawSectorCard() {
		return (SectorCard)sDeck.drawCard();
	}

	public ObjectCard drawObjectCard() {
		return (ObjectCard)oDeck.drawCard();
	}

	public SectorCard drawEscapeCard() {
		return (SectorCard)eDeck.drawCard();
	}

}
