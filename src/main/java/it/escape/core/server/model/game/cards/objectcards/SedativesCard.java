package it.escape.core.server.model.game.cards.objectcards;

import it.escape.core.server.controller.game.actions.ObjectCardAction;
import it.escape.core.server.controller.game.actions.objectcardactions.Sedatives;
import it.escape.core.server.model.game.cards.Card;
import it.escape.core.server.model.game.cards.ObjectCard;

public class SedativesCard implements ObjectCard,Card {

	public ObjectCardAction getObjectAction() {
		return new Sedatives();
	}

	@Override
	public String toString() {
		return ("ObjectCard : " + getClass().getSimpleName());
	}

}
