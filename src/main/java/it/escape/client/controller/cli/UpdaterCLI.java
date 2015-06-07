package it.escape.client.controller.cli;

import it.escape.client.controller.MessageCarrier;
import it.escape.client.controller.Updater;

import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;

/**This class Observes the MessageCarrier, that receives Strings from the Connection.
 * This class checks if the messages sent by the Server correspond
 * to one of the given patterns; if they do, the TurnInputState is set.
 */
public class UpdaterCLI extends Updater implements Observer {
	
	private StateManagerCLIInterface stateRef;
	
	/**
	 * 
	 */
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
	
	/**This method checks if the String message received from the Server 
	 * corresponds to any of the given patterns.
	 * If it does, it sets the appropriate TurnInputState.
	 * @param message
	 */
	protected void processMessage(String message) {
		Matcher obj = inputObjectCard.matcher(message);
		Matcher pos = inputPosition.matcher(message);
		Matcher yesno = inputYesNo.matcher(message);
		Matcher turnstart = myTurnStart.matcher(message);
		Matcher turnend = myTurnEnd.matcher(message);
		if (obj.matches()) {
			stateRef.setObjectCardState();
		} else if (pos.matches()) {
			stateRef.setPositionState();
		} else if (yesno.matches()) {
			stateRef.setYesNoState();
		} else if (turnstart.matches()) {
			stateRef.setMyTurn();
		} else if (turnend.matches()) {
			stateRef.setNotMyTurn();
		}
	}

}
