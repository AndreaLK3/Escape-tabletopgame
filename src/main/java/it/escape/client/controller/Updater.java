package it.escape.client.controller;

import it.escape.client.MessageCarrier;
import it.escape.client.model.PlayerState;
import it.escape.client.view.Terminal;
import it.escape.strings.FormatToPattern;
import it.escape.strings.StringRes;

import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Updater implements Observer {
	
	private StateManager stateRef;
	
	private Terminal view;
	
	private Pattern inputObjectCard;
	private Pattern inputPosition;
	private Pattern inputYesNo;
	
	
	public Updater(StateManager stateRef, Terminal view) {
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
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof MessageCarrier) {
			MessageCarrier msg = (MessageCarrier) arg0;
			processMessage(msg.getMessage());
			// view show message
		}
	}
	
	private void processMessage(String message) {
		Matcher obj = inputObjectCard.matcher(message);
		Matcher pos = inputPosition.matcher(message);
		Matcher yesno = inputYesNo.matcher(message);
		if (obj.matches()) {
			stateRef.setObjectCardState();
		} else if (pos.matches()) {
			stateRef.setPositionState();
		} else if (yesno.matches()) {
			stateRef.setYesNoState();
		}
	}

}
