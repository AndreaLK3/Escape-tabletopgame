package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.character.Action;

public class CellaSicura extends Cella implements Settore {

	public CellaSicura(PositionCubic position) {
		super(position);
	}

	public void eseguiAzione(Action esecutore) {
		esecutore.noAction();
	}

}
