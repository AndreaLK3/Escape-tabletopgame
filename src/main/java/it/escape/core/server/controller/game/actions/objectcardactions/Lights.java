package it.escape.core.server.controller.game.actions.objectcardactions;

import it.escape.core.server.controller.Shorthand;
import it.escape.core.server.controller.UserMessagesReporter;
import it.escape.core.server.controller.game.actions.HumanActionInterface;
import it.escape.core.server.controller.game.actions.MapActionInterface;
import it.escape.core.server.controller.game.actions.ObjectCardAction;
import it.escape.core.server.controller.game.actions.PlayerActionInterface;
import it.escape.core.server.model.game.exceptions.BadCoordinatesException;
import it.escape.core.server.model.game.exceptions.CellNotExistsException;
import it.escape.core.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.core.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.tools.strings.StringRes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Lights implements ObjectCardAction {
	
	private static final Logger LOGGER = Logger.getLogger( Lights.class.getName() );

	@Override
	public void execute(HumanActionInterface currentHuman, MapActionInterface map) {
		PlayerActionInterface currentPlayer = (PlayerActionInterface) currentHuman;
		String posAlphaNum;
		PositionCubic pos3D=null;
		List <PositionCubic> sectors = new ArrayList<PositionCubic>();
		boolean correctInput;
		
		do{
			posAlphaNum =  UserMessagesReporter.getReporterInstance(currentPlayer).askForLightsPosition(map.getPlayerAlphaNumPosition(currentPlayer));
			try {
				pos3D = CoordinatesConverter.fromAlphaNumToCubic(posAlphaNum);
				sectors = map.getNeighborPositions(pos3D);
				sectors.add(pos3D);	//add the center (is it necessary?)
				correctInput = true;
			}
			catch (BadCoordinatesException e) {
				UserMessagesReporter.getReporterInstance(currentPlayer).relayMessage(
						StringRes.getString("messaging.exceptions.badCoordinatesFormat"));
				correctInput = false;
				LOGGER.log(Level.FINER, "bad coordinates format", e);
			}
			catch (CellNotExistsException e) {
				UserMessagesReporter.getReporterInstance(currentPlayer).relayMessage(
						StringRes.getString("messaging.exceptions.cellNotExists"));
				correctInput=false;
				LOGGER.log(Level.FINER, "cell doesn't exist on the map", e);
			}
		} while (!correctInput);	
		
		for (PositionCubic s : sectors) {
			List<PlayerActionInterface> playersFound = map.getPlayersByPosition(s);
			for (PlayerActionInterface p: playersFound) 
				Shorthand.announcer(currentPlayer).announcePlayerPosition(p, s);
		}
		return;
		
	}
	
}
