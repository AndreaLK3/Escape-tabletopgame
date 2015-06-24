package it.escape.core.server.model.game.cards;

import it.escape.core.server.controller.game.actions.DecksHandlerInterface;


/**This class initializes the decks, which contain different kinds of cards, and makes them visible.
 * n: It is a singleton.
 * @author andrea
 */
public class DecksHandler implements DecksHandlerInterface{
	
	private EscapeDeck eDeck;
	private SectorDeck sDeck;
	private ObjectDeck oDeck;
	
	public DecksHandler() {
		eDeck = new EscapeDeck();
		sDeck = new SectorDeck();
		oDeck = new ObjectDeck();
	}

	@Override
	public SectorCard drawSectorCard() {
		return (SectorCard)sDeck.drawCard();
	}

	@Override
	public ObjectCard drawObjectCard() {
		return (ObjectCard)oDeck.drawCard();
	}

	@Override
	public SectorCard drawEscapeCard() {
		return (SectorCard)eDeck.drawCard();
	}

}
