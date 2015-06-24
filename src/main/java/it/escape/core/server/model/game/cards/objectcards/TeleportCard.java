package it.escape.core.server.model.game.cards.objectcards;

import it.escape.core.server.controller.game.actions.ObjectCardAction;
import it.escape.core.server.controller.game.actions.objectcardactions.Teleport;
import it.escape.core.server.model.game.cards.Card;
import it.escape.core.server.model.game.cards.ObjectCard;

public class TeleportCard implements Card, ObjectCard{

	public ObjectCardAction getObjectAction() {
	
		return new Teleport();
	}
	
	@Override
	public String toString() {
		return ("ObjectCard : " + getClass().getSimpleName());
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof TeleportCard) {
			return true;
		}
		else {
			return false;
		}
	}

}
