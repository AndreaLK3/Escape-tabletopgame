package it.escape.core.client.controller;

import it.escape.tools.strings.FormatToPattern;
import it.escape.tools.strings.StringRes;

import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Pattern;

public abstract class Updater implements Observer {  

	protected Pattern inputObjectCard;
	protected Pattern inputPosition;
	protected Pattern inputYesNo;
	protected Pattern turn_Start;
	protected Pattern turn_End;
	protected Pattern turn_playOrDiscard;
	protected Pattern setGameMap;
	protected Pattern getMOTDstart;
	protected Pattern getMOTDend;
	protected Pattern startInXSeconds;
	protected Pattern inboundChatMessage;
	
	
	
	
	public Updater() {
		initPatterns();
	}

	public abstract void update(Observable arg0, Object arg1);
	
	public abstract void processMessage(String message) throws RemoteException;
	
	protected void initPatterns() {
		inputObjectCard = new FormatToPattern(StringRes.getString("messaging.askWhichObjectCard")).convert();
		inputPosition = new FormatToPattern(StringRes.getString("messaging.askForPosition")).convert();
		inputYesNo = Pattern.compile(String.format(
				StringRes.getString("messaging.askBinaryChoice"),
				"yes",
				"no"));
		turn_Start = new FormatToPattern(StringRes.getString("messaging.hail.player")).convert();
		turn_End = new FormatToPattern(StringRes.getString("messaging.farewell")).convert();
		setGameMap = new FormatToPattern(StringRes.getString("messaging.serversMap")).convert();
		getMOTDstart = new FormatToPattern(StringRes.getString("messaging.motd.start")).convert();
		getMOTDend = new FormatToPattern(StringRes.getString("messaging.motd.end")).convert();
		startInXSeconds = new FormatToPattern(StringRes.getString("messaging.gameStartETA")).convert();
		inboundChatMessage = new FormatToPattern(StringRes.getString("messaging.relayChat")).convert();
		turn_playOrDiscard =  new FormatToPattern(StringRes.getString("messaging.tooManyCardsHuman")).convert();
		
	};
	
}
