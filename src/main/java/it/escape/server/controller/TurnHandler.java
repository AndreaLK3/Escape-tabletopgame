package it.escape.server.controller;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.CellAction;
import it.escape.server.controller.game.actions.DecksHandlerInterface;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.cardactions.DrawObjectCard;
import it.escape.server.controller.game.actions.playercommands.MoveCommand;
import it.escape.server.model.game.cards.ObjectCard;
import it.escape.server.model.game.exceptions.BadCoordinatesException;
import it.escape.server.model.game.exceptions.CardNotPresentException;
import it.escape.server.model.game.exceptions.CellNotExistsException;
import it.escape.server.model.game.exceptions.DestinationUnreachableException;
import it.escape.server.model.game.exceptions.PlayerCanNotEnterException;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.players.Player;
import it.escape.strings.StringRes;

import java.util.logging.Logger;

/** This class defines the order of execution of the various
 * methods that are invoked during a turn.
 * Since the order of execution may vary depending on user input,
 * it will contain a reference to an UserMessagesReporter object.
 * The 2 subclasses, HumanTurnHandler and AlienTurnHandler,
 * implement the function executeTurnSequence() in different ways.
 * @author andrea, michele
 */
public abstract class TurnHandler {
	
	protected static final Logger LOGGER = Logger.getLogger( TurnHandler.class.getName() );
	
	protected UserMessagesReporter reporter;
	protected CardAction cardAction;
	protected CellAction cellAction;
	protected MoveCommand moveCommand;
	protected ObjectCard objectCard;
	protected boolean correctInput;
	protected boolean endObjectCard;
	
	protected Player currentPlayer;
	
	protected MapActionInterface map;
	
	protected DecksHandlerInterface decks;
	
	public TurnHandler (MapActionInterface map, DecksHandlerInterface decks) {
		this.map = map;
		this.decks = decks;
	}
	
	/**
	 * standard turn sequence, Human and Alien may implement the various steps
	 * differently, but they remain the same nonetheless.
	 */
	public void executeTurnSequence() {
		hailPlayer();
		if (currentPlayer.isUserIdle()) {  // player idle
			LOGGER.fine(StringRes.getString("controller.turnhandler.skipIdle"));
		} else {
			if (currentPlayer.isAlive()) {  // player respondind and alive
				initialize();  // step 0
				turnBeforeMove();  // step 1
				turnMove();  // step 2
				turnLand();  // also step 2
				turnAfterMove();  // step 3
				deInitialize();  // cleanup (stop filling default options)
			} else {  // player responding but dead
				LOGGER.fine(StringRes.getString("controller.turnhandler.skipDead"));
			}
		}
		farewellPlayer();
	}
	
	private void hailPlayer() {
		if (currentPlayer.isAlive()) {
			UserMessagesReporter.getReporterInstance(currentPlayer).reportStartMyTurn(
					currentPlayer.getName(),
					CoordinatesConverter.fromCubicToAlphaNum(map.getPlayerPosition(currentPlayer)));
		} else {
			UserMessagesReporter.getReporterInstance(currentPlayer).relayMessage(String.format(
					StringRes.getString("messaging.hail.deadPlayer"),
					currentPlayer.getName()));
		}
	}
	
	private void farewellPlayer() {
		UserMessagesReporter.getReporterInstance(currentPlayer).reportEndTurn();
	}
	
	protected void discardObjectCard() {
		String key=null;
		do {
			try {
				String defaultCardName = currentPlayer.getMyHand().getCardName(0);
				key = reporter.askWhichObjectCard(defaultCardName);
				objectCard = currentPlayer.drawCard(key);  // card is removed from the player's hand	
				endObjectCard = true;
				
			} catch (CardNotPresentException e) {	//CardNotExistingException
				LOGGER.finer(e.getClass().getSimpleName() + " " + e.getMessage());
				endObjectCard = false;
			}
		} while (!endObjectCard);
		reporter.relayMessage(String.format(StringRes.getString("messaging.discardedCard"),key));
	}
	
	/**
	 * Part of turnLanding which is the same in both humans and aliens
	 * (trigger the sector-specific action, draw an object card if
	 * needed). This sequence may not be executed, depending on whether
	 * the player has attacked/ is sedated.
	 */
	protected void commonLandingLogic() {
		cardAction = cellAction.execute(currentPlayer, map, decks);
		LOGGER.finer(currentPlayer.getName() + " has drawn: " + cardAction.getClass().getSimpleName());
		cardAction.execute(currentPlayer, map, decks);  // make noise/silence/whatever
		if (cardAction.hasObjectCard()) {
			cardAction = new DrawObjectCard();
			cardAction.execute(currentPlayer, map, decks);  // actually draw the object card
			}
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
				moveCommand = reporter.askForMovement(map.getPlayerAlphaNumPosition(currentPlayer));
				try {
					cellAction = moveCommand.execute(currentPlayer, map);
					correctInput = true;
					currentPlayer.setHasMoved(true);
				} catch (PlayerCanNotEnterException e) {
					String exceptionMessage = e.getClass().getSimpleName() + " : " + e.getMessage();
					LOGGER.finer(exceptionMessage);
					reporter.relayMessage(exceptionMessage);
				} catch (BadCoordinatesException e) {
					String exceptionMessage = e.getClass().getSimpleName() + " : " + e.getMessage();
					LOGGER.finer(exceptionMessage);
					reporter.relayMessage(exceptionMessage);
				} catch (CellNotExistsException e) {
					String exceptionMessage = e.getClass().getSimpleName() + " : " + e.getMessage();
					LOGGER.finer(exceptionMessage);
					reporter.relayMessage(exceptionMessage);
				} catch (DestinationUnreachableException e) {
					String exceptionMessage = e.getClass().getSimpleName() + " : " + e.getMessage();
					LOGGER.finer(exceptionMessage);
					reporter.relayMessage(exceptionMessage);
				} 
			
			} while (!correctInput);
		
		// say where I am after the move
		UserMessagesReporter.getReporterInstance(currentPlayer).reportMyUserPosition(
				map.getPlayerAlphaNumPosition(currentPlayer));
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
