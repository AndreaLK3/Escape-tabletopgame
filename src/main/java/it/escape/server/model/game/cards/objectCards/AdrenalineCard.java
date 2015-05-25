package it.escape.server.model.game.cards.objectCards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.controller.game.actions.objectcardactions.Adrenaline;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.cards.ObjectCard;

public class AdrenalineCard implements ObjectCard, Card{

	public ObjectCardAction getObjectAction() {
		
		return new Adrenaline();
	}

	public CardAction getCardAction() {
		
		return null;
	}

}