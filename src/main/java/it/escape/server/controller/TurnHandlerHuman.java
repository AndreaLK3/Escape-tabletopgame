package it.escape.server.controller;


import it.escape.server.model.game.actions.PlayerCommand;
import it.escape.server.model.game.character.Player;

public class TurnHandlerHuman extends TurnHandler {

	
	
	
	public TurnHandlerHuman(Player currentPlayer) {
		super(currentPlayer);
	}

	public void executeTurnSequence() {
		reporter = UserMessagesReporter.getReporterInstance();
		
		playerCommand = reporter.askForObjectCard("Do you wish to play an object card before moving?");

		playerCommand.execute(currentPlayer);
		
		playerCommand = reporter.askForPosition("Now you have to move. Where do you want to go?");
		
		playerCommand.execute(currentPlayer);
		}


}
