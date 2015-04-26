package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.character.Action;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public class CellaPericolosa extends Cella {

	public CellaPericolosa(PositionCubic position) {
		super(position);
	}

	@Override
	public void eseguiAzione(Action esecutore) {
		esecutore.drawCard();
	}

	@Override
	public boolean isWalkable(Action esecutore) {
		return true;
	}

}
