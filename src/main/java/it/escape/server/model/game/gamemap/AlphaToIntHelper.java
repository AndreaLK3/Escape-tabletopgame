package it.escape.server.model.game.gamemap;

/**
 * 
 * @author michele
 * La classe viene utilizzata per convertire una string di caratteri A-Z
 * nella corrispondente rappresentazione come valore intero
 */
public final class AlphaToIntHelper {
	
	private final String rawValue;
	
	// range usato solo per la conversione dei simboli, non ha relazione con la
	// dimensione della mappa
	private final char RANGE_START = 'A';
	private final char RANGE_END = 'Z';

	public AlphaToIntHelper(String rawValue) {
		this.rawValue = rawValue;
	}
	/**
	 * 
	 * @param lettera carattere da convertire
	 * @return intero corrispondente nell'alfabeto A-Z
	 */
	private int asciiToInt(char lettera) {
		return (int) lettera - RANGE_START;
	}
	/**
	 * 
	 * @return restituisce la rappresentazione numerica della stringa
	 */
	public int convert() {
		int total = 0;
		int range = RANGE_END - RANGE_START;
		
		for (int i = 0; i < rawValue.length(); i++) {
			int curr = asciiToInt(rawValue.charAt(i));
			// we won't check if curr belongs to our rage, as is guaranteed by the caller
			total += curr * Math.pow(range, rawValue.length() - i -1);
		}
		return total;
	}
}
