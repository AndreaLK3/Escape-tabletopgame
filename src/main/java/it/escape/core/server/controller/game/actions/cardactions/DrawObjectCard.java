package it.escape.core.server.controller.game.actions.cardactions;

import it.escape.core.server.controller.UserMessagesReporter;
import it.escape.core.server.controller.game.actions.CardAction;
import it.escape.core.server.controller.game.actions.DecksHandlerInterface;
import it.escape.core.server.controller.game.actions.MapActionInterface;
import it.escape.core.server.controller.game.actions.PlayerActionInterface;
import it.escape.core.server.model.game.cards.ObjectCard;

public class DrawObjectCard implements CardAction{

	public void execute(PlayerActionInterface currentPlayer, MapActionInterface map, DecksHandlerInterface deck) {
		ObjectCard card = (ObjectCard) deck.drawObjectCard();
		UserMessagesReporter.getReporterInstance(currentPlayer).reportObjectCardDrawn(
				card.getClass().getSimpleName());
		currentPlayer.addCard(card);
	}

	public boolean hasObjectCard() {
		return false;
	}

}
