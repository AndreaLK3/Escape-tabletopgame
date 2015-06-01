package it.escape.server.controller.game.actions.playercommands;

import it.escape.server.controller.Shorthand;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

import java.util.List;

public class Attack {

		public void execute(PlayerActionInterface currentPlayer, MapActionInterface map) {
			PositionCubic myPos = map.getPlayerPosition(currentPlayer);
			Shorthand.announcer(currentPlayer).announceAttack(currentPlayer, myPos);
			List<PlayerActionInterface> targets = map.getPlayersByPosition(myPos);
			for (PlayerActionInterface p : targets) {
				if (p.isAlive() && !p.equals(currentPlayer)) {
					p.die();
					if (!p.isAlive()) {
						Shorthand.announcer(currentPlayer).announceDeath(p);
					}
				}
			}
		}
	

}
