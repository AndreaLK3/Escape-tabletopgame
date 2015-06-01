package it.escape.server.controller.game.actions.cardactions;

import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.DecksHandlerInterface;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.model.game.cards.ObjectCard;
import it.escape.strings.StringRes;

public class DrawObjectCard implements CardAction{

	public void execute(PlayerActionInterface currentPlayer, MapActionInterface map, DecksHandlerInterface deck) {
		ObjectCard card = (ObjectCard) deck.drawObjectCard();
		UserMessagesReporter.getReporterInstance(currentPlayer).relayMessage(String.format(
				StringRes.getString("messaging.objectCardDrawn"),
				card.getClass().getSimpleName()));
		currentPlayer.addCard(card);
	}

	public boolean hasObjectCard() {
		return false;
	}

}
