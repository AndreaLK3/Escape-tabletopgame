package it.escape.server.model.game.gamemap.loader;

import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.gamemap.Cell;
import it.escape.utils.FilesHelper;
import it.escape.utils.LogHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class MapLoaderMain {

	protected static final Logger log = Logger.getLogger( MapLoaderMain.class.getName() );
	
	public static void main(String[] args) {
		LogHelper.setDefaultOptions(log);
		try {
			InputStream file = FilesHelper.getResourceFile("resources/test_map.json");
			MapLoader loader = new MapLoader(file);
			
			for (Cell c : loader) {
				log.fine(c.toString());
			}
			
		} catch (FileNotFoundException e) {
			log.severe("file not found");
		} catch (BadJsonFileException e) {
			log.severe("bad json file");
		} catch (IOException e) {
			log.severe("file not readable");
		}
	}
}
