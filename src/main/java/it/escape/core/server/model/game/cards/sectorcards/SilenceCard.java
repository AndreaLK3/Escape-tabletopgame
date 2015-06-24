package it.escape.core.server.model.game.cards.sectorcards;

import it.escape.core.server.controller.game.actions.CardAction;
import it.escape.core.server.controller.game.actions.cardactions.Silence;
import it.escape.core.server.model.game.cards.Card;
import it.escape.core.server.model.game.cards.SectorCard;

public class SilenceCard implements Card,SectorCard {

	@Override
	public CardAction getCardAction() {
		return new Silence();
	}
	

}
