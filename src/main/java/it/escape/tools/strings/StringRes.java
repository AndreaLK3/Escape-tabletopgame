package it.escape.tools.strings;

import it.escape.tools.utils.FilesHelper;

import java.io.IOException;
import java.util.MissingResourceException;
import java.util.Properties;

/**
 * classe statica che permette di accedere a tutte le stringhe di testo
 * usate dal gioco, realizzando così la separazione codice/dati
 * @author michele
 *
 */
public class StringRes {
	
	private static Properties res = null;
	
	public static void loadProperties() throws IOException {
		String resourceName = "resources/Strings.properties";
		res = new Properties();
		res.load(FilesHelper.getResourceFile(resourceName));
	}
	
	public static String getString(String key) {
		if (res == null) {
			try {
				loadProperties();
			} catch (IOException e) {
				return "Strings file not found";
			}
		}
		try {
			return res.getProperty(key);
		} catch (MissingResourceException e) {
			return "String not found";
		}
	}
}
