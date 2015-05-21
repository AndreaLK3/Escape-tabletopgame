package it.escape.server.controller.game.actions.playerCommands;

import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.controller.game.actions.PlayerCommand;
import it.escape.server.controller.game.actions.objectCardActions.Sedatives;
import it.escape.server.model.game.players.Human;

/**This class, depending on user input, returns different ObjectCardActions
 * @author andrea
 */
public class ChooseObjectCard implements PlayerCommand {
	
	public static ObjectCardAction execute(Human currentPlayer) {
		String cardName = UserMessagesReporter.getReporterInstance(currentPlayer).askWhichObjectCard();
		//to implement : check the Player's Hand, and see if he has the required Card
		// for now, 
		return new Sedatives();
		
	}

}
