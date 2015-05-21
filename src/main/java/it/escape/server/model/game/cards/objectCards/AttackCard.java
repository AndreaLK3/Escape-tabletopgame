package it.escape.server.model.game.cards.objectCards;

import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.controller.game.actions.objectCardActions.Attack;

public class AttackCard implements ObjectCard {

	public ObjectCardAction getObjectAction() {
		
		return new Attack();
	}

}
