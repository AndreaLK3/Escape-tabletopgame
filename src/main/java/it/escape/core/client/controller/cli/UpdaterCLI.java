package it.escape.core.client.controller.cli;

import it.escape.core.client.controller.Updater;
import it.escape.tools.MessageCarrier;

import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;

/**This class Observes the Connection 
 * (n: that means it observes the MessageCarrier, that receives Strings from the Connection)(.
 * This class checks if the messages sent by the ServerSocketCore correspond
 * to one of the given patterns; if they do, the TurnInputState is set.
 * N: Possible implementation for RMI: the methods of the stateManager can be made public and invoked remotely*/

public class UpdaterCLI extends Updater implements Observer {
	
	private StateManagerCLIInterface stateRef;
	
	private UpdaterCLItoTerminalInterface view;
	
	
	
	public UpdaterCLI(StateManagerCLIInterface stateRef, UpdaterCLItoTerminalInterface view) {
		super();
		this.stateRef = stateRef;
		this.view = view;
	}
	
	

	/**When the MessageCarrier notifies this class,
	 * the message is processed (setting TurnInputState) and 
	 * then it is visualized on the Terminal View.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof MessageCarrier) {
			MessageCarrier msg = (MessageCarrier) arg0;
			processMessage(msg.getMessage());
			view.visualizeMessage(msg.getMessage());
		}
	}
	
	/**This method checks if the String message received from the ServerSocketCore 
	 * corresponds to any of the given patterns.
	 * If it does, it sets the appropriate TurnInputState.
	 * @param message
	 */
	public void processMessage(String message) {
		Matcher objectRequired = input_ObjectCard.matcher(message);
		Matcher positionRequired = input_Position.matcher(message);
		Matcher yesnoRequired = input_YesNo.matcher(message);
		Matcher turnstart = turn_Start.matcher(message);
		Matcher turnend = turn_End.matcher(message);
		Matcher playvsdiscard = turn_playOrDiscard.matcher(message);
		if (objectRequired.matches()) {
			stateRef.setObjectCardState();
		} else if (positionRequired.matches()) {
			stateRef.setPositionState();
		} else if (yesnoRequired.matches()) {
			stateRef.setYesNoState();
		} else if (turnstart.matches()) {
			stateRef.setMyTurn();
		} else if (turnend.matches()) {
			stateRef.setNotMyTurn();
		} else if (playvsdiscard.matches()) {
			stateRef.setOtherChoice();;
		}
	}

}
