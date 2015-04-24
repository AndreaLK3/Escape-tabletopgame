package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.character.Action;

public class CellaPericolosa extends Cella implements Settore {

	public CellaPericolosa(PositionCubic position) {
		super(position);
	}

	public void eseguiAzione(Action esecutore) {
		esecutore.drawCard();
	}

}
