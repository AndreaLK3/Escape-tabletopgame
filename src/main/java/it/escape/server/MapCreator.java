package it.escape.server;

import java.io.IOException;
import java.util.logging.Logger;

import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.exceptions.MalformedStartingCells;
import it.escape.server.model.game.gamemap.GameMap;
import it.escape.utils.LogHelper;

public class MapCreator {
	
	protected static final Logger log = Logger.getLogger( MapCreator.class.getName() );
	
	private String mapfile;

	public MapCreator(String mapfile) {
		LogHelper.setDefaultOptions(log);
		this.mapfile = mapfile;
	}
	
	public GameMap getMap() {
		try {
			return new GameMap(mapfile);
			
		} catch (BadJsonFileException e) {
			crash(e.getMessage());
		} catch (IOException e) {
			crash(e.getMessage());
		} catch (MalformedStartingCells e) {
			crash(e.getMessage());
		}
		
		return null;
	}
	
	private void crash(String message) {
		log.severe(message);
		throw new AssertionError();
	}

}
