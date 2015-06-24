package it.escape.core.server.controller.game.actions.objectcardactions;

import it.escape.core.server.controller.UserMessagesReporter;
import it.escape.core.server.controller.game.actions.HumanActionInterface;
import it.escape.core.server.controller.game.actions.MapActionInterface;
import it.escape.core.server.controller.game.actions.ObjectCardAction;
import it.escape.core.server.model.game.gamemap.positioning.PositionCubic;

public class Teleport implements ObjectCardAction {

	@Override
	public void execute(HumanActionInterface currentHuman, MapActionInterface map) {
		PositionCubic c = map.getStartHumans();
		map.updatePlayerPosition(currentHuman, c);
		
		// say where I am after the move
		UserMessagesReporter.getReporterInstance(currentHuman).reportMyUserPosition(
				map.getPlayerAlphaNumPosition(currentHuman));
	}

}
