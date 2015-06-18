package it.escape.server;

import java.io.IOException;
import java.util.logging.Logger;

import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.exceptions.MalformedStartingCells;
import it.escape.server.model.game.gamemap.GameMap;
import it.escape.utils.FilesHelper;
import it.escape.utils.LogHelper;

public class MapCreator {
	
	protected static final Logger log = Logger.getLogger( MapCreator.class.getName() );
	
	private String[] maprotation;
	private int current;
	
	private String mapfile;
	
	/**
	 * Constructor. Does not check if any of the maps in
	 * maprotation is valid
	 * @param maprotation array of map names, consecutive GameMaster will run consecutive maps
	 */
	public MapCreator(String[] maprotation) {
		LogHelper.setDefaultOptions(log);
		this.maprotation = maprotation;
		current = -1;
		if (maprotation.length == 0) {
			crash("Invalid map rotation");
		}
	}
	
	/**
	 * Convert a maprotation from string to array format
	 * @param input comma separated list of map names
	 * @return
	 */
	public static String[] stringToMapRotation(String input) {
		return input.split(",");
	}
	
	private void nextMap() {
		current++;
		if (current >= maprotation.length) {
			current = 0;
		}
		mapfile = FilesHelper.mapFileFromName(maprotation[current]);
	}
	
	public GameMap getMap() {
		try {
			nextMap();
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
