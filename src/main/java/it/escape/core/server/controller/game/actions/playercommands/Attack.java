package it.escape.core.server.controller.game.actions.playercommands;

import it.escape.core.server.controller.Shorthand;
import it.escape.core.server.controller.UserMessagesReporter;
import it.escape.core.server.controller.game.actions.MapActionInterface;
import it.escape.core.server.controller.game.actions.PlayerActionInterface;
import it.escape.core.server.model.game.gamemap.positioning.PositionCubic;

import java.util.List;

public class Attack {

	/**This method does:
	 * 1)Tells the Announcer to broadcast the news of an Attack, announcing attacker and position.
	 * 2)For all targeted Players in the Cell (minus the attacker himself), executes the method p.die(),
	 * 	that kills the targets unless they have a defense card.
	 * 3)Announces a death or a successful defense in the location.
	 * 4)Congratulates the players that defended themselves.*/
		public void execute(PlayerActionInterface currentPlayer, MapActionInterface map) {
			PositionCubic myPos = map.getPlayerPosition(currentPlayer);
			Shorthand.announcer(currentPlayer).announceAttack(currentPlayer, myPos);
			
			List<PlayerActionInterface> targets = map.getPlayersByPosition(myPos);
			
			for (PlayerActionInterface p : targets) {
				
				if (p.isAlive() && !p.equals(currentPlayer)) {
					p.die();
					if (!p.isAlive()) {
						currentPlayer.attackEndedSuccessfully();
						Shorthand.announcer(currentPlayer).announceDeath(p);
					} else {
						Shorthand.announcer(currentPlayer).announceDefense(myPos);
						UserMessagesReporter.getReporterInstance(p).reportDefense();
					}
				}
			}
		}
	

}
