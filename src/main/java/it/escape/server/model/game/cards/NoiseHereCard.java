package it.escape.server.model.game.cards;

import it.escape.server.model.game.character.AzioneCarta;

public class NoiseHereCard implements Card {

	@Override
	public void effetc(AzioneCarta esecutore) {
		esecutore.noiseInMySector();
	}

}
