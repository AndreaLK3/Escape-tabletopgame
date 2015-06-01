package it.escape.server.model.game.cards;

import it.escape.server.controller.game.actions.DecksHandlerInterface;


/**This class initializes the decks, which contain different kinds of cards, and makes them visible.
 * n: It is a singleton.
 * @author andrea
 */
public class DecksHandler implements DecksHandlerInterface{
	
	private EscapeDeck eDeck;
	private SectorDeck sDeck;
	private ObjectDeck oDeck;
	
	private static DecksHandler decksHandlerInstance;
	
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
