package it.escape.server.controller.game.actions.cellactions;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.CellAction;
import it.escape.server.controller.game.actions.DecksHandlerInterface;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.model.game.players.Player;

public class GetSectorCardAction implements CellAction {

	public CardAction execute(Player currentPlayer, MapActionInterface map, DecksHandlerInterface deck) {
			return deck.drawSectorCard().getCardAction();
	}

}
