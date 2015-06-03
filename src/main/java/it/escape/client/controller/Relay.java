package it.escape.client.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import it.escape.client.ClientSocketInterface;

public class Relay {
	
	private ClientSocketInterface communication;

	public Relay(ClientSocketInterface communication) {
		this.communication = communication;
	}
	
	public void relayMessage(String message) {
		communication.sendMessage(message);
	}
	
	public boolean checkCardsFormat(String message) {
		List<String> cardnames = new ArrayList<String>(Arrays.asList("teleport", "lights", "sedatives", "adrenaline"));
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
}
