package it.escape.server.controller;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.CellAction;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.playercommands.MoveCommand;

/** This class defines the order of execution of the various
 * methods that are invoked during a turn.
 * Since the order of execution may vary depending on user input,
 * it will contain a reference to an UserMessagesReporter object.
 * The 2 subclasses, HumanTurnHandler and AlienTurnHandler,
 * implement the function executeTurnSequence() in different ways.
 * @author andrea, michele
 */
public abstract class TurnHandler {
	
	protected CardAction cardAction;
	protected CellAction cellAction;
	protected MoveCommand moveCommand;
	protected boolean correctInput;
	
	protected MapActionInterface map;
	
	public TurnHandler (MapActionInterface map) {
		this.map = map;
		correctInput = false;
	}
	
	/**
	 * standard turn sequence, Human and Alien may implement the various steps
	 * differently, but they remain the same nonetheless.
	 */
	public void executeTurnSequence() {
		initialize();  // step 0
		turnBeforeMove();  // step 1
		turnMove();  // step 2
		turnLand();  // also step 2
		turnAfterMove();  // step 3
		}
	
	/**
	 * prepare the turn-handler (setup Reporter, prepare currentPlayer)
	 */
	public abstract void initialize();
	
	/**
	 * actions performed before moving (in the human: playing an object card)
	 */
	public abstract void turnBeforeMove();
	
	/**
	 * only the movement itself
	 */
	public abstract void turnMove();
	
	/**
	 * actions performed upon landing on a new cell
	 * (picking up sector card and maybe an object card too)
	 */
	public abstract void turnLand();
	
	/**
	 * actions performed after moving (in the human: playing an object card)
	 */
	public abstract void turnAfterMove();
	
}
