package it.escape.client.controller.cli;

import it.escape.client.controller.MessageCarrier;
import it.escape.strings.FormatToPattern;
import it.escape.strings.StringRes;

import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**This class Observes the MessageCarrier, that receives Strings from the Connection.
 * This class checks if the messages sent by the Server correspond
 * to one of the given patterns; if they do, the TurnInputState is set.
 */
public class UpdaterCLI implements Observer {
	
	private StateManagerCLIInterface stateRef;
	
	private UpdaterCLItoTerminalInterface view;
	
	private Pattern inputObjectCard;
	private Pattern inputPosition;
	private Pattern inputYesNo;
	private Pattern myTurnStart;
	private Pattern myTurnEnd;
	
	
	public UpdaterCLI(StateManagerCLIInterface stateRef, UpdaterCLItoTerminalInterface view) {
		this.stateRef = stateRef;
		this.view = view;
		initPatterns();
	}
	
	private void initPatterns() {
		inputObjectCard = new FormatToPattern(StringRes.getString("messaging.askWhichObjectCard")).convert();
		inputPosition = new FormatToPattern(StringRes.getString("messaging.askForPosition")).convert();
		inputYesNo = Pattern.compile(String.format(
				StringRes.getString("messaging.askBinaryChoice"),
				"yes",
				"no"));
		myTurnStart = new FormatToPattern(StringRes.getString("messaging.hail.player")).convert();
		myTurnEnd = new FormatToPattern(StringRes.getString("messaging.farewell")).convert();
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof MessageCarrier) {
			MessageCarrier msg = (MessageCarrier) arg0;
			processMessage(msg.getMessage());
			view.visualizeMessage(msg.getMessage());
		}
	}
	
	private void processMessage(String message) {
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
