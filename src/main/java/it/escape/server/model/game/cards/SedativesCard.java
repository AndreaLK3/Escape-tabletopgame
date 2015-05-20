package it.escape.server.model.game.cards;

import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.controller.game.actions.objectCardActions.Sedatives;

public class SedativesCard implements ObjectCard {

	public ObjectCardAction getObjectAction() {
		return new Sedatives();
	}

}
