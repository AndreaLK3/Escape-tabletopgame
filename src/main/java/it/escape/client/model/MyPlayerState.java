package it.escape.client.model;

import it.escape.server.model.game.players.PlayerTeams;

import java.util.ArrayList;
import java.util.List;

/**This class contains all the informations in PlayerState, 
 * plus: myTeam, and a List<String of myObjectCards
 * It must (yet) be updated by the UpdaterSwing in client.controller.
 */
public class MyPlayerState extends PlayerState {
	
	
	private String myTeam;
	
	private List<String> objectCards;
	
	
	public MyPlayerState() {
		super();
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

	public String getMyTeam() {
		return myTeam;
	}

	public void setMyTeam(String myTeam) {
		this.myTeam = myTeam;
	}




}
