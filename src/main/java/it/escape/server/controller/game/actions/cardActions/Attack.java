package it.escape.server.controller.game.actions.cardActions;

import java.util.List;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.Player;

public class Attack implements CardAction {

	public void execute(Player currentPlayer, MapActionInterface map) {
		PositionCubic myPos = map.getPlayerPosition(currentPlayer);
		Announcer.getAnnouncerInstance().announceAttack(currentPlayer, myPos);
		List<Player> targets = map.getPlayersByPosition(myPos);
		for (Player p : targets) {
			p.die();
		}
	}

	public boolean hasObjectCard() {
		return false;
	}

}
