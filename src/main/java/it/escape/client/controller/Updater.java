package it.escape.client.controller;

import it.escape.strings.FormatToPattern;
import it.escape.strings.StringRes;

import java.util.Observable;
import java.util.Observer;
import java.util.regex.Pattern;

public abstract class Updater implements Observer {  

	protected Pattern inputObjectCard;
	protected Pattern inputPosition;
	protected Pattern inputYesNo;
	protected Pattern myTurnStart;
	protected Pattern myTurnEnd;
	protected Pattern setGameMap;
	protected Pattern getMOTD;
	
	public Updater() {
		initPatterns();
	}

	public abstract void update(Observable arg0, Object arg1);
	
	protected abstract void processMessage(String message);
	
	protected void initPatterns() {
		inputObjectCard = new FormatToPattern(StringRes.getString("messaging.askWhichObjectCard")).convert();
		inputPosition = new FormatToPattern(StringRes.getString("messaging.askForPosition")).convert();
		inputYesNo = Pattern.compile(String.format(
				StringRes.getString("messaging.askBinaryChoice"),
				"yes",
				"no"));
		myTurnStart = new FormatToPattern(StringRes.getString("messaging.hail.player")).convert();
		myTurnEnd = new FormatToPattern(StringRes.getString("messaging.farewell")).convert();
		setGameMap = new FormatToPattern(StringRes.getString("messaging.serversMap")).convert();
		getMOTD = new FormatToPattern(StringRes.getString("messaging.motd")).convert();
	};
	
}
