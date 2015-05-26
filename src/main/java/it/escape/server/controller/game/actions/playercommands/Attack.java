package it.escape.server.controller.game.actions.playercommands;

import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;

import java.util.List;

public class Attack {

		public void execute(Player currentPlayer, MapActionInterface map) {
			PositionCubic myPos = map.getPlayerPosition(currentPlayer);
			Announcer.getAnnouncerInstance().announceAttack(currentPlayer, myPos);
			List<Player> targets = map.getPlayersByPosition(myPos);
			for (Player p : targets) {
				if (p.isAlive()) {
					p.die();
					if (!p.isAlive()) {
						Announcer.getAnnouncerInstance().announceDeath(p);
					}
				}
			}
		}
	

}
