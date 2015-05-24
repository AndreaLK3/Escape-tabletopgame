package it.escape.server.model.game.cards.objectCards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.controller.game.actions.objectcardactions.Defense;
import it.escape.server.model.game.cards.Card;

public class DefenseCard implements ObjectCard, Card {

	public CardAction getCardAction() {
		return null;
	}

	public ObjectCardAction getObjectAction() {
		return new Defense();
	}

}
