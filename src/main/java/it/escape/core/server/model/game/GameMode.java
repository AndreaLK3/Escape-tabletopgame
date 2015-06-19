package it.escape.core.server.model.game;

/**(Temporary implementation)
 * A game can use 2 modes: Base or Complete.
 * This class contains the current game mode.
 * (n: it will probably be modified later, since the server must handle several games at once...)
 * (It could also be made static)
 * @author andrea
 */
public class GameMode {
	
	private GameTypes type;
	
	public GameMode (GameTypes type) {
		this.type = type;
	}

	public GameTypes getType() {
		return type;
	}
	

}
