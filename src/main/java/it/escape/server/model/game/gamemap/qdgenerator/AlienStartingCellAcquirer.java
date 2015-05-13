package it.escape.server.model.game.gamemap.qdgenerator;

import it.escape.strings.StringRes;

public class AlienStartingCellAcquirer extends CellAcquirer {

	@Override
	public String getPromptText() {
		return StringRes.getString("mapfile.qdgenerator.input.Cellapartenza.alieni");
	}

	@Override
	protected String getTypeString() {
		return StringRes.getString("mapfile.stringToClass.Cellapartenza.alieni");
	}

	@Override
	protected CellAcquirer createNextState() {
		return new EscapeCellAcquirer();
	}

	@Override
	protected CellAcquirer createMyState() {
		return new EscapeCellAcquirer(); // we will read only ONE alien starting cell
	}

}
