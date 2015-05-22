package it.escape.server.controller.game.actions.card.actions;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.cards.objectCards.ObjectCard;
import it.escape.server.model.game.players.Player;

public class DrawObjectCard implements CardAction{

	public void execute(Player currentPlayer, MapActionInterface map) {
		ObjectCard card = (ObjectCard) DecksHandler.getDecksHandler().drawObjectCard();
		currentPlayer.addCard(card);
	}

	public boolean hasObjectCard() {
		return false;
	}

}
