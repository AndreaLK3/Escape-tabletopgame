package it.escape.server.model.game.gamemap.qdgenerator;

import it.escape.strings.StringRes;

public class HumanStartingCellAcquirer extends CellAcquirer {

	@Override
	public String getPromptText() {
		return StringRes.getString("mapfile.qdgenerator.input.Cellapartenza.umani");
	}

	@Override
	protected String getTypeString() {
		return StringRes.getString("mapfile.stringToClass.Cellapartenza.umani");
	}

	@Override
	protected CellAcquirer createNextState() {
		return new AlienStartingCellAcquirer();
	}

	@Override
	protected CellAcquirer createMyState() {
		return new AlienStartingCellAcquirer(); // we will read only ONE human starting cell
	}

}
