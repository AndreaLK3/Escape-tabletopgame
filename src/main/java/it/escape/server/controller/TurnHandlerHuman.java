package it.escape.server.controller;


import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;

public class TurnHandlerHuman extends TurnHandler {

	private Human currentPlayer;
	private UserMessagesReporter reporter;
	private ObjectCardAction objectCardAction;
	
	public TurnHandlerHuman(Player currentPlayer, MapActionInterface map) {
		super(map);
		this.currentPlayer=(Human)currentPlayer;
	}

	public void executeTurnSequence() {
		reporter = UserMessagesReporter. getReporterInstance(currentPlayer);
		currentPlayer.startTurn();
		
		/*if (reporter.askIfObjectCard("Do you want to play an object card before moving?"));
		{	do {
				try {
					objectCardAction = ChooseObjectCard.execute(currentPlayer);
					correctInput = true;
				} catch (Exception e) {	//DestinationNotInRangeException, DestinationNotExistingException
					correctInput = false;
				}
				} while (!correctInput);
		objectCardAction.execute(currentPlayer, map);
		}*/
			
		
		moveCommand = reporter.askForMovement();
		
		do {
		try {
			cellAction = moveCommand.execute(currentPlayer, map);
			correctInput = true;
		} catch (Exception e) {	//DestinationNotInRangeException, DestinationNotExistingException
			correctInput = false;
		}} while (!correctInput);
		 
		if(!currentPlayer.hasSedatives())
			cardAction = cellAction.execute(currentPlayer, map);
		
		if (reporter.askIfObjectCard("Do you wish to play an object card after moving?"));
		//ask for an Object Card. The Player has the hand...
		
	
		cardAction.execute(currentPlayer, map);
		
		}


}
