package it.escape.server.controller.game.actions.playercommands;

import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.controller.game.actions.PlayerCommand;
import it.escape.server.controller.game.actions.objectcard.actions.Sedatives;
import it.escape.server.model.game.players.Human;

/**This class, depending on user input, returns different ObjectCardActions
 * @author andrea
 */
public class ChooseObjectCard implements PlayerCommand {
	
	private ChooseObjectCard () {
		
	}
	
	public static ObjectCardAction execute(Human currentPlayer) {
		String cardName = UserMessagesReporter.getReporterInstance(currentPlayer).askWhichObjectCard();
		//to implement : check the Player's Hand, and see if he has the required Card
		// to implement : check if the Player has moved or not, and check what kind of card he wants to play
		// for now, 
		return new Sedatives();
		
	}

}
