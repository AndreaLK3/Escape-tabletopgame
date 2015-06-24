package it.escape.core.server.model.game.cards.objectcards;

import it.escape.core.server.controller.game.actions.ObjectCardAction;
import it.escape.core.server.controller.game.actions.objectcardactions.Lights;
import it.escape.core.server.model.game.cards.Card;
import it.escape.core.server.model.game.cards.ObjectCard;

public class LightsCard  implements Card, ObjectCard{

	public ObjectCardAction getObjectAction() {
	
		return new Lights();
	}

	@Override
	public String toString() {
		return ("ObjectCard : " + getClass().getSimpleName());
	}
	

}
