package it.escape.server.controller.game.actions.objectcardactions;

import java.util.ArrayList;
import java.util.List;

import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.model.game.exceptions.BadCoordinatesException;
import it.escape.server.model.game.exceptions.CellNotExistsException;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;
import it.escape.strings.StringRes;

public class Lights implements ObjectCardAction {

	public void execute(Human currentPlayer, MapActionInterface map) {
		String posAlphaNum;
		PositionCubic pos3D=null;
		List <Cell> cells = new ArrayList<Cell>();
		boolean correctInput;
		
		do{
			posAlphaNum =  UserMessagesReporter.getReporterInstance(currentPlayer).askForLightsPosition();
			try {
				pos3D = CoordinatesConverter.fromAlphaNumToCubic(posAlphaNum);
				cells = map.getNeighbors(pos3D);
				cells.add(map.getCell(pos3D));	//add the center (is it necessary?)
				correctInput = true;
			}
			catch (BadCoordinatesException e) {
				UserMessagesReporter.getReporterInstance(currentPlayer).relayMessage(
						StringRes.getString("messaging.badCoordinatesFormat"));
				//NOTE: It would be better to transfer the format check either to the client or to the UMR 
				correctInput = false;
			}
			catch (CellNotExistsException e) {
				UserMessagesReporter.getReporterInstance(currentPlayer).relayMessage(
						StringRes.getString("messaging.cellNotExists"));
				correctInput=false;
			}
		} while (!correctInput);	
		
		for (Cell c : cells) {
			List<Player> playersFound = map.getPlayersByPosition(c.getPosition());
			for (Player p: playersFound) 
				Announcer.getAnnouncerInstance().announcePlayerPosition(p, c.getPosition());
		}
		return;
		
	}
	
}
