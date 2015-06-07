package it.escape.client.controller.cli;

import it.escape.strings.FormatToPattern;
import it.escape.strings.StringRes;

import java.util.Observable;
import java.util.Observer;
import java.util.regex.Pattern;

public class Updater implements Observer {

	protected Pattern inputObjectCard;
	protected Pattern inputPosition;
	protected Pattern inputYesNo;
	protected Pattern myTurnStart;
	protected Pattern myTurnEnd;
	
	
	public void update(Observable arg0, Object arg1){};
	
	private void processMessage(String message){};
	
	protected void initPatterns() {
		inputObjectCard = new FormatToPattern(StringRes.getString("messaging.askWhichObjectCard")).convert();
		inputPosition = new FormatToPattern(StringRes.getString("messaging.askForPosition")).convert();
		inputYesNo = Pattern.compile(String.format(
				StringRes.getString("messaging.askBinaryChoice"),
				"yes",
				"no"));
		myTurnStart = new FormatToPattern(StringRes.getString("messaging.hail.player")).convert();
		myTurnEnd = new FormatToPattern(StringRes.getString("messaging.farewell")).convert();
		
	};
	
}
