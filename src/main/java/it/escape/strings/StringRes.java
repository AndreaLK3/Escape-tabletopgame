package it.escape.strings;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * classe statica che permette di accedere a tutte le stringhe di testo
 * usate dal gioco, realizzando così la separazione codice/dati
 * @author michele
 *
 */
public class StringRes {
	
	private static ResourceBundle res = ResourceBundle.getBundle("Strings");
	
	public static String getString(String key) {
		try {
			return res.getString(key);
		} catch (MissingResourceException e) {
			return "String not found";
		}
	}
}
