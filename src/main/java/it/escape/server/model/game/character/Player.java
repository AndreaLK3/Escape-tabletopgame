package it.escape.server.model.game.character;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.cards.Deck;
import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.gamemap.GameMap;

public class Player {

	protected Card aCard;
	
	public Player () {
	}
	

	/**This method is overridden by the Human and Alien subclasses*/
	public PlayerTeams getTeam(){
		return null;
	};






}