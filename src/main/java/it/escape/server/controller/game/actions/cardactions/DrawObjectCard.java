package it.escape.server.controller.game.actions.cardactions;

import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.DecksHandlerInterface;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.cards.ObjectCard;
import it.escape.server.model.game.players.Player;
import it.escape.strings.StringRes;

public class DrawObjectCard implements CardAction{

	public void execute(Player currentPlayer, MapActionInterface map, DecksHandlerInterface deck) {
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
