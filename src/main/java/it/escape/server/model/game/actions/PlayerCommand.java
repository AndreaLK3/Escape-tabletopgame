package it.escape.server.model.game.actions;

import it.escape.server.model.game.character.Player;

public interface PlayerCommand {

	public void execute (Player curPlayer);
}
