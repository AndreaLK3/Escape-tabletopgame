package it.escape.core.server.model.game.gamemap.qdgenerator;

import it.escape.tools.strings.StringRes;

public class SafeCellAcquirer extends CellAcquirer {

	@Override
	public String getPromptText() {
		return StringRes.getString("mapfile.qdgenerator.input.CellaSicura");
	}

	@Override
	protected String getTypeString() {
		return StringRes.getString("mapfile.stringToClass.CellaSicura");
	}

	@Override
	protected CellAcquirer createNextState() {
		return new DangerousCellAcquirer();
	}

	@Override
	protected CellAcquirer createMyState() {
		return new SafeCellAcquirer();
	}

}
