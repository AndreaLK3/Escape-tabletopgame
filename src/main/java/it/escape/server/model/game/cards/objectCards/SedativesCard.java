package it.escape.server.model.game.cards.objectCards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.controller.game.actions.objectcard.actions.Sedatives;
import it.escape.server.model.game.cards.Card;

public class SedativesCard implements ObjectCard, Card {

	public ObjectCardAction getObjectAction() {
		return new Sedatives();
	}

	public CardAction getCardAction() {
		return null;
	}

}
