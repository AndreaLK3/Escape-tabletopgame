package it.escape.server.controller;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.CellAction;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.playercommands.MoveCommand;
import it.escape.server.model.game.cards.ObjectCard;
import it.escape.server.model.game.exceptions.CardNotPresentException;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;

/** This class defines the order of execution of the various
 * methods that are invoked during a turn.
 * Since the order of execution may vary depending on user input,
 * it will contain a reference to an UserMessagesReporter object.
 * The 2 subclasses, HumanTurnHandler and AlienTurnHandler,
 * implement the function executeTurnSequence() in different ways.
 * @author andrea, michele
 */
public abstract class TurnHandler {
	
	protected UserMessagesReporter reporter;
	protected CardAction cardAction;
	protected CellAction cellAction;
	protected MoveCommand moveCommand;
	protected ObjectCard objectCard;
	protected boolean correctInput;
	protected boolean endObjectCard;
	
	protected Player currentPlayer;
	
	protected MapActionInterface map;
	
	public TurnHandler (MapActionInterface map) {
		this.map = map;
	}
	
	/**
	 * standard turn sequence, Human and Alien may implement the various steps
	 * differently, but they remain the same nonetheless.
	 */
	public void executeTurnSequence() {
		if (currentPlayer.isAlive()) {
			initialize();  // step 0
			turnBeforeMove();  // step 1
			turnMove();  // step 2
			turnLand();  // also step 2
			turnAfterMove();  // step 3
			deInitialize();  // cleanup (stop filling default options)
		}
	}
	
	protected void discardObjectCard() {
		do {
			try {
				String key = reporter.askWhichObjectCard();
				objectCard = currentPlayer.drawCard(key);  // card is removed from the player's hand	
				endObjectCard = true;
				
			} catch (CardNotPresentException e) {	//CardNotExistingException
				endObjectCard = false;
			}
		} while (!endObjectCard);
	}
	
	
	/**This function is called by the thread that runs inside the TimeController class.
	 * Meanwhile, the thread of ExecutiveController&TurnHandler is stagnating somewhere... 
	 */
	public void fillInDefaultChoices() {
		reporter.fillinDefaultOnce();  // free us from the block we're currently stuck in
		reporter.fillinDefaultAlways();  // free us from future blocks
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
	 *The movement itself. Since the sequence of invoked methods is the same for Humans and Aliens, 
	 *as Sonar suggests, it is inside the superclass. 
	 */
	public void turnMove() {
		do {
				moveCommand = reporter.askForMovement();
				try {
					cellAction = moveCommand.execute(currentPlayer, map);
					correctInput = true;
				} catch (Exception e) {
					e.getMessage();
				}
			
			} while (!correctInput);

		
	}
	
	/**
	 * actions performed upon landing on a new cell
	 * (picking up sector card and maybe an object card too)
	 */
	public abstract void turnLand();
	
	/**
	 * actions performed after moving (in the human: playing an object card)
	 */
	public abstract void turnAfterMove();
	
	/**
	 * "service" actions that do not directly affect the player
	 */
	public abstract void deInitialize();
}
