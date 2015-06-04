package it.escape.client.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import it.escape.client.ClientSocketInterface;
import it.escape.strings.FormatToPattern;
import it.escape.strings.StringRes;

public class Relay {
	
	private ClientSocketInterface communication;

	public Relay(ClientSocketInterface communication) {
		this.communication = communication;
	}
	
	public void relayMessage(String message) {
		communication.sendMessage(message);
	}
	
	public void disconnectNow() {
		communication.killConnection();
	}
	
	public boolean checkCardsFormat(String message) {
		List<String> cardnames = new ArrayList<String>(Arrays.asList("attack", "defense", "teleport", "lights", "sedatives", "adrenaline"));
		if (cardnames.contains(message)) {
			return true;
		}
		return false;
	}
	
	public boolean checkYesNoFormat(String message) {
		List<String> cardnames = new ArrayList<String>(Arrays.asList("yes", "no"));
		if (cardnames.contains(message)) {
			return true;
		}
		return false;
	}
	
	public boolean checkPositionFormat(String message) {
		Pattern pos = Pattern.compile("([A-Z]+)([0-9]+)");
		if (pos.matcher(message).matches()) {
			return true;
		}
		return false;
	}
	
	public boolean checkChatFormat(String message) {
		Pattern chat = new FormatToPattern(StringRes.getString("messaging.chat")).convert();
		if (chat.matcher(message).matches()) {
			return true;
		}
		return false;
	}
	
	public boolean checkRenameFormat(String message) {
		Pattern rename = new FormatToPattern(StringRes.getString("messaging.renameMyself")).convert();
		if (rename.matcher(message).matches()) {
			return true;
		}
		return false;
	}
	
	public boolean checkFreeMessage(String message) {
		if (checkChatFormat(message) || checkRenameFormat(message)) {
			return true;
		}
		return false;
	}
}
