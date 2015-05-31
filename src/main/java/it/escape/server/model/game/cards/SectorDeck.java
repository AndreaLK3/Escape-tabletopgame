package it.escape.server.model.game.cards;

import it.escape.server.model.game.cards.sectorcards.NoiseAnywhereCard;
import it.escape.server.model.game.cards.sectorcards.NoiseAnywhereCardWithObject;
import it.escape.server.model.game.cards.sectorcards.NoiseHereCard;
import it.escape.server.model.game.cards.sectorcards.NoiseHereCardWithObject;
import it.escape.server.model.game.cards.sectorcards.SilenceCard;
import it.escape.server.model.game.cards.sectorcards.SilenceCardWithObject;

public class SectorDeck extends Deck {

	/** Symbolic constants define the number of cards of each kind in this deck*/
	private final static int NOISEHERE = 3;
	private final static int NOISEANYWHERE = 3;
	private final static int SILENCE = 3;
	private final static int NOISEHEREWITHOBJECT = 3;
	private final static int NOISEANYWHEREWITHOBJECT = 3;
	private final static int SILENCEWITHOBJECT = 3;
	
	public SectorDeck() {
		super();
		int i;
		
		for (i=0; i<NOISEHERE; i++)
		{	theDeck.add(new NoiseHereCard());
			
		}
		for (i=0; i<NOISEANYWHERE; i++)
		{	theDeck.add(new NoiseAnywhereCard());
			
		}
		for (i=0; i<SILENCE; i++)
		{	theDeck.add(new SilenceCard());
			
		}
		for (i=0; i<NOISEHEREWITHOBJECT; i++)
		{	theDeck.add(new NoiseHereCardWithObject());
			
		}
		for (i=0; i<NOISEANYWHEREWITHOBJECT; i++)
		{	theDeck.add(new NoiseAnywhereCardWithObject());
			
		}
		for (i=0; i<SILENCEWITHOBJECT; i++)
		{	theDeck.add(new SilenceCardWithObject());
			
		}
		
		this.shuffleDeck();
	}
}
