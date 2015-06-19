package it.escape.core.server.model.game.cards;

import it.escape.core.server.model.game.cards.objectcards.AdrenalineCard;
import it.escape.core.server.model.game.cards.objectcards.AttackCard;
import it.escape.core.server.model.game.cards.objectcards.DefenseCard;
import it.escape.core.server.model.game.cards.objectcards.LightsCard;
import it.escape.core.server.model.game.cards.objectcards.SedativesCard;
import it.escape.core.server.model.game.cards.objectcards.TeleportCard;

public class ObjectDeck extends Deck {
	
	/** Symbolic constants define the number of cards of each kind in this deck*/
	private final static int ATTACK = 4;
	private final static int DEFENSE = 4;
	private final static int TELEPORT = 4;
	private final static int LIGHTS = 4;
	private final static int ADRENALINE = 4;
	private final static int SEDATIVES = 4;
	
	public ObjectDeck() {
		super();
		int i;
		
		for (i=0; i<ATTACK; i++) {
			theDeck.add(new AttackCard());
		}
		for (i=0; i<DEFENSE; i++) {
			theDeck.add(new DefenseCard());
		}
		for (i=0; i<TELEPORT; i++) {
			theDeck.add(new TeleportCard());
		}
		for (i=0; i<LIGHTS; i++) {
			theDeck.add(new LightsCard());
		}
		for (i=0; i<ADRENALINE; i++) {
			theDeck.add(new AdrenalineCard());
		}
		for (i=0; i<SEDATIVES; i++) {
			theDeck.add(new SedativesCard());
			
		}
		
		this.shuffleDeck();
	}
}
