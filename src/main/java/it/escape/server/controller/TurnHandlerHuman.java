package it.escape.server.controller;


import it.escape.server.model.game.actions.playerCommands.PlayerCommand;
import it.escape.server.model.game.character.Human;
import it.escape.server.model.game.character.Player;

public class TurnHandlerHuman extends TurnHandler {

	Human currentPlayer;
	UserMessagesReporter reporter;
	
	
	public TurnHandlerHuman(Player currentPlayer) {
		this.currentPlayer=(Human)currentPlayer;
	}

	public void executeTurnSequence() {
		reporter = UserMessagesReporter.getReporterInstance();
		
		if (reporter.askIfObjectCard("Do you want to play an object card before moving?"));
			//asks if the user wants to play an Object Card. The Player has the hand...
		
		playerCommand = reporter.askForMovement("Now you have to move. Where do you want to go?");
		try {
			playerCommand.execute(currentPlayer);
		} catch (Exception e) {	//DestinationNotInRangeException, DestinationNotExistingException
		}
		 
		if(currentPlayer.sedatives == false)
			cardAction = cellAction.execute(currentPlayer);
		
		if (reporter.askIfObjectCard("Do you wish to play an object card after moving?"));
		//ask for an Object Card. The Player has the hand...
		
	
		cardAction.execute(currentPlayer);
		
		}


}
