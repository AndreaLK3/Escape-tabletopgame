package it.escape.client;

import java.util.ArrayList;
import java.util.List;

public class PlayerState {
	
	private List<String> objectCards;
	
	
	public PlayerState() {
		objectCards = new ArrayList<String>();
		addCard("Attack");
		addCard("Teleport");
	}
	
	public void addCard(String cardName) {
		objectCards.add(cardName);
	}
	
	public List<String> getObjectCards() {
		return objectCards;
	}


}
