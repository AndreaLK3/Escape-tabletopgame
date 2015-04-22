package it.escape.server.model.game.gamemap;

public final class AlphaToIntHelper {
	
	private final String rawValue;
	
	private final char RANGE_START = 'A';
	private final char RANGE_END = 'Z';
	

	public AlphaToIntHelper(String rawValue) {
		this.rawValue = rawValue;
	}
	
	private int asciiToInt(char coso) {
		return (int) coso - RANGE_START;
	}
	
	public int convert() {
		int total = 0;
		int range = RANGE_END - RANGE_START;
		
		for (int i = 0; i < rawValue.length(); i++) {
			int curr = asciiToInt(rawValue.charAt(i));
			// TODO check curr belongs to the specified range
			total += curr * Math.pow(range, rawValue.length() - i -1);
		}
		return total;
	}
}
