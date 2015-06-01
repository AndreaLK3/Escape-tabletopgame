package it.escape.server.controller.game.actions.cellactions;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.CellAction;
import it.escape.server.controller.game.actions.DecksHandlerInterface;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.PlayerActionInterface;

public class GetSectorCardAction implements CellAction {

	public CardAction execute(PlayerActionInterface currentPlayer, MapActionInterface map, DecksHandlerInterface deck) {
			return deck.drawSectorCard().getCardAction();
	}

}
