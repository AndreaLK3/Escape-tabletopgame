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
	
	/** This method returns a copy of the original list, not a reference to it.*/
	public List<String> getObjectCards() {
		List<String> objectCardsCopy = new ArrayList<String>();
		for (String s : objectCards) {
			objectCardsCopy.add(s);
		}
		return objectCardsCopy;
	}


}
