package it.escape.core.server.model.game.cards.objectcards;

import it.escape.core.server.controller.game.actions.ObjectCardAction;
import it.escape.core.server.controller.game.actions.objectcardactions.Adrenaline;
import it.escape.core.server.model.game.cards.Card;
import it.escape.core.server.model.game.cards.ObjectCard;

public class AdrenalineCard implements ObjectCard, Card{

	public ObjectCardAction getObjectAction() {
		
		return new Adrenaline();
	}

	@Override
	public String toString() {
		return ("ObjectCard : " + getClass().getSimpleName());
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof AdrenalineCard) {
			return true;
		}
		else {
			return false;
		}
	}
	

}
