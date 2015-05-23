package it.escape.server.model.game.cards.objectCards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.controller.game.actions.objectcardactions.Attack;
import it.escape.server.model.game.cards.Card;

public class AttackCard implements ObjectCard, Card {

	public ObjectCardAction getObjectAction() {
		
		return new Attack();
	}

	public CardAction getCardAction() {
		return null;
	}

}
