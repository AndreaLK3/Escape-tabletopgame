package it.escape.client.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import it.escape.strings.FormatToPattern;
import it.escape.strings.StringRes;

/**This class contains the methods to check the format of the messages,
 * according to the type of input requested, such as 
 * checkCardsFormat(String s), checkYesNoFormat(String s).
 * Moreover:
 * The method relayMessage(String message) gives the connection the order to send a message.
 * The method disconnectNow() gives the connection the order to terminate.
 */
public class Relay {
	
	private ClientSocketChannelInterface communication;

	public Relay(ClientSocketChannelInterface communication) {
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
	
	public void sendChat(String message) {
		String ready = String.format(StringRes.getString("messaging.chat"), message);
		relayMessage(ready);
	}
	
	public void renameSelf(String name) {
		String ready = String.format(StringRes.getString("messaging.renameMyself"), name);
		relayMessage(ready);
		// to get a confirm from the server, ask "whoami"; the updater will take care
		// of updating the displayed name accordingly
		ready = StringRes.getString("messaging.whoami");
		relayMessage(ready);
		
		
	}
}
