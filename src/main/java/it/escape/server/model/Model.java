package it.escape.server.model;

import java.io.IOException;
import java.util.logging.Logger;

import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.exceptions.MalformedStartingCells;
import it.escape.server.model.game.gamemap.GameMap;
import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

/**
 * Model class: contains the map and the announcer, to be easily
 * managed by the GameMaster
 * @author michele
 *
 */
public class Model {
	
	protected static final Logger log = Logger.getLogger( Model.class.getName() );
	
	private GameMap theMap;
	
	private Announcer theAnnouncer;

	public Model(String mapname) {
		LogHelper.setDefaultOptions(log);
		
		try {
			theMap = GameMap.createMapInstance(mapname);
			theAnnouncer = new Announcer();
			
		} catch (BadJsonFileException e) {
			crash(StringRes.getString("model.error.json") + " (" + e.getMessage() + ")");
		} catch (IOException e) {
			crash(StringRes.getString("model.error.ioerror") + " (" + e.getMessage() + ")");
		} catch (MalformedStartingCells e) {
			crash(StringRes.getString("model.error.malformedMap") + " (" + e.getMessage() + ")");
		}
	}
	
	/**
	 * Stops the program in case of unrecoverable errors
	 * @param message
	 */
	private void crash(String message) {
		log.severe(message);
		throw new AssertionError();
	}

	public GameMap getMap() {
		return theMap;
	}

	public Announcer getAnnouncer() {
		return theAnnouncer;
	}
	
}
