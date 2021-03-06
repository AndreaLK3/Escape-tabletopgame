package it.escape.core.server.model.game.cards.objectcards;

import it.escape.core.server.controller.game.actions.ObjectCardAction;
import it.escape.core.server.controller.game.actions.objectcardactions.Teleport;
import it.escape.core.server.model.game.cards.Card;
import it.escape.core.server.model.game.cards.ObjectCard;

public class TeleportCard implements Card, ObjectCard{

	@Override
	public ObjectCardAction getObjectAction() {
		return new Teleport();
	}
	
	@Override
	public String toString() {
		return "ObjectCard : " + getClass().getSimpleName();
	}


}
