package it.escape.core.server.model.game.cards.objectcards;

import it.escape.core.server.controller.game.actions.ObjectCardAction;
import it.escape.core.server.controller.game.actions.objectcardactions.Defense;
import it.escape.core.server.model.game.cards.Card;
import it.escape.core.server.model.game.cards.ObjectCard;

public class DefenseCard implements ObjectCard, Card {

	@Override
	public ObjectCardAction getObjectAction() {
		return new Defense();
	}

	@Override
	public String toString() {
		return "ObjectCard : " + getClass().getSimpleName();
	}

}
