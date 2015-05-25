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
				//send a message to the UMR : ask for correct Input
				//NOTE: It would be better to transfer the format check either to the client or to the UMR 
				correctInput = false;
			}
			catch (CellNotExistsException e) {
				//send a message to the UMR : ask for correct Input
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
