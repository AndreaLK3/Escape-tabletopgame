package it.escape.server.controller.game.actions.cellActions;

import it.escape.server.controller.game.actions.cardActions.CardAction;
import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.players.Player;

public class DrawSectorCard implements CellAction {

	public CardAction execute(Player currentPlayer) {
			return DecksHandler.getDecksHandler().drawSectorCard().getCardAction();
	}

}
