package it.escape.server.model.game.cards;

public class SectorDeck extends Deck {

	/** Symbolic constants define the number of cards of each kind in this deck*/
	private final static int NOISEHERE = 5;
	private final static int NOISEANYWHERE = 5;
	private final static int SILENCE = 5;
	
	public SectorDeck() {
		int i;
		counter = 0;
		
		for (i=0; i<NOISEHERE; i++)
		{	theDeck.add(new NoiseHereCard());
			counter++;
		}
		for (i=0; i<NOISEANYWHERE; i++)
		{	theDeck.add(new NoiseAnywhereCard());
			counter++;
		}
		for (i=0; i<SILENCE; i++)
		{	theDeck.add(new SilenceCard());
			counter++;
		}
		
		this.shuffleDeck();
	}
}
