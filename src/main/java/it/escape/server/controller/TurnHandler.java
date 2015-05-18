package it.escape.server.controller;

import it.escape.server.model.game.actions.cardActions.CardAction;
import it.escape.server.model.game.actions.cellActions.CellAction;
import it.escape.server.model.game.actions.playerCommands.PlayerCommand;

/** This class defines the order of execution of the various
 * methods that are invoked during a turn.
 * Since the order of execution may vary depending on user input,
 * it will contain a reference to an UserMessagesReporter object (n: UserMessagesReporter is a Singleton).
 * The 2 subclasses, HumanTurnHandler and AlienTurnHandler,
 * implement the function turnSequence() in different ways.
 * @author andrea
 */
public class TurnHandler {
	
	
	UserMessagesReporter reporter;
	CardAction cardAction;
	CellAction cellAction;
	PlayerCommand playerCommand;
	
	
	public TurnHandler () {
		
	}
	
	public void executeTurnSequence() {} ;
	
}
