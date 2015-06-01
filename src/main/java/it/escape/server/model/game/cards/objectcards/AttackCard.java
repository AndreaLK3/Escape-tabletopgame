package it.escape.server.model.game.cards.objectcards;

import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.controller.game.actions.objectcardactions.AttackOrder;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.cards.ObjectCard;

public class AttackCard implements ObjectCard, Card {

	public ObjectCardAction getObjectAction() {
		
		return new AttackOrder();
	}


}
