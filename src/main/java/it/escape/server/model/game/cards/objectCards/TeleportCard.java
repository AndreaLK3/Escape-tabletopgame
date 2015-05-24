package it.escape.server.model.game.cards.objectCards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.controller.game.actions.objectcardactions.Teleport;
import it.escape.server.model.game.cards.Card;

public class TeleportCard implements Card, ObjectCard{

	public ObjectCardAction getObjectAction() {
	
		return new Teleport();
	}

	public CardAction getCardAction() {
		
		return null;
	}

}
