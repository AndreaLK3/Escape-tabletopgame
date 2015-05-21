package it.escape.server.controller;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.CellAction;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.controller.game.actions.playerCommands.MoveCommand;

/** This class defines the order of execution of the various
 * methods that are invoked during a turn.
 * Since the order of execution may vary depending on user input,
 * it will contain a reference to an UserMessagesReporter object.
 * The 2 subclasses, HumanTurnHandler and AlienTurnHandler,
 * implement the function executeTurnSequence() in different ways.
 * @author andrea
 */
public class TurnHandler {
	
	protected CardAction cardAction;
	protected CellAction cellAction;
	protected MoveCommand moveCommand;
	protected boolean correctInput;
	
	protected MapActionInterface map;
	
	public TurnHandler (MapActionInterface map) {
		this.map = map;
		correctInput = false;
	}
	
	public void executeTurnSequence() {} ;
	
}
