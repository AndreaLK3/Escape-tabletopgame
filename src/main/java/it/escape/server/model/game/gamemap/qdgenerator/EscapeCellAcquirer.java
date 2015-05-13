package it.escape.server.model.game.gamemap.qdgenerator;

import it.escape.strings.StringRes;

public class EscapeCellAcquirer extends CellAcquirer {

	@Override
	public String getPromptText() {
		return StringRes.getString("mapfile.qdgenerator.input.CellaScialuppa");
	}

	@Override
	protected String getTypeString() {
		return StringRes.getString("mapfile.stringToClass.CellaScialuppa");
	}

	@Override
	protected CellAcquirer createNextState() {
		return null;  // final cell to be acquired
	}

	@Override
	protected CellAcquirer createMyState() {
		return new EscapeCellAcquirer();
	}

}
