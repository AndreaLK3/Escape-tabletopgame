package it.escape.server.controller.game.actions.objectcardactions;

import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.Human;

public class Lights implements ObjectCardAction {

	public void execute(Human currentPlayer, MapActionInterface map) {
		String posAlphaNum;
		
		do {
			posAlphaNum =  UserMessagesReporter.getReporterInstance(currentPlayer).askForLightsPosition();
		} while(!map.cellExists(posAlphaNum));
		
		
	}

}
