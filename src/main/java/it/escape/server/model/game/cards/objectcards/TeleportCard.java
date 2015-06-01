package it.escape.server.model.game.cards.objectcards;

import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.controller.game.actions.objectcardactions.Teleport;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.cards.ObjectCard;

public class TeleportCard implements Card, ObjectCard{

	public ObjectCardAction getObjectAction() {
	
		return new Teleport();
	}

}
