package it.escape.client.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerState {
	
	private List<String> objectCards;
	
	
	public PlayerState() {
		objectCards = new ArrayList<String>();
	}
	
	public void addCard(String cardName) {
		objectCards.add(cardName);
	}
	
	public List<String> getObjectCards() {
		return objectCards;
	}


}
