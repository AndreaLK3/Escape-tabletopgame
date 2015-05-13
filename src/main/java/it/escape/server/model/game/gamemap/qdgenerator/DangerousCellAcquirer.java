package it.escape.server.model.game.gamemap.qdgenerator;

import it.escape.strings.StringRes;

public class DangerousCellAcquirer extends CellAcquirer {

	@Override
	public String getPromptText() {
		return StringRes.getString("mapfile.qdgenerator.input.CellaPericolosa");
	}

	@Override
	protected String getTypeString() {
		return StringRes.getString("mapfile.stringToClass.CellaPericolosa");
	}

	@Override
	protected CellAcquirer createNextState() {
		return new HumanStartingCellAcquirer();
	}

	@Override
	protected CellAcquirer createMyState() {
		return new DangerousCellAcquirer();
	}

}
