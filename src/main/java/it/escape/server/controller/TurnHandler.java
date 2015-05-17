package it.escape.server.controller;

import it.escape.server.model.game.character.Player;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

import java.util.ArrayList;
import java.util.List;

/** This class defines the order of execution of the various
 * methods that are invoked during a turn.
 * Since the order of execution may vary depending on user input,
 * it will contain a reference to an UserInputReceiver object.
 * The 2 subclasses, HumanTurnHandler and AlienTurnHandler,
 * implement the function turnSequence in different ways.
 * @author andrea
 */
public class TurnHandler {
	
	protected Player currentPlayer;
	/*protected UserInputReceiver theUser;*/
	
	public TurnHandler (Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	public void turnSequence() {} ;
	
}
