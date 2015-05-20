package it.escape.server.model.game.actions.cardActions;

import java.util.List;

import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.gamemap.GameMap;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.Player;

public class Attack implements CardAction {

	public void execute(Player currentPlayer) {
		PositionCubic myPos = GameMap.getMapInstance().getPlayerPosition(currentPlayer);
		Announcer.getAnnouncerInstance().announceAttack(currentPlayer, myPos);
		List<Player> targets = GameMap.getMapInstance().getPlayersByPosition(myPos);
		for (Player p : targets) {
			p.die();
		}
	}

	public boolean hasObjectCard() {
		return false;
	}

}
