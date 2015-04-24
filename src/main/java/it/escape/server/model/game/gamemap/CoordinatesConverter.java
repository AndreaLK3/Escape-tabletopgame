package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.gamemap.exceptions.BadCoordinatesException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoordinatesConverter {
	
	// regex che corrisponde a una stringa costruita così: <lettere maiuscole><numeri>
	public static final Pattern PATTERN = Pattern.compile("([A-Z]+)([0-9]+)");
	
	public static Position2D fromCubicToOddQ(PositionCubic posC) {
		Integer col;
		Integer row;
		
		col = posC.getX();
		row = ( posC.getZ() + (posC.getX() - (posC.getX()&1)) / 2 );
		
		return new Position2D(col, row);
	}
	
	public static String fromCubicToAlphaNum(PositionCubic posC) {
		
		Position2D mypos = fromCubicToOddQ(posC);
		
		Character col = new Character((char) ('A' + mypos.getX()));
		
		return new String(col.toString() + mypos.getY());
	}
	
	/**
	 * 
	 * @param coordstring stringa del tipo "AAA000"
	 * @throws BadCoordinatesException
	 */
	public PositionCubic fromAlphaNumToCubic(String coordstring) throws BadCoordinatesException {
		int x, y, z;
		
		Position2D coord = fromAlphaToOddq(coordstring);
		Integer col = coord.getX();
		Integer row = coord.getY();
		
		x = col;
		y = row - ((col + (col&1)) / 2);
		z = -x - y;
		return new PositionCubic (x, y, z);
	}
	
	
	
	public Position2D fromAlphaToOddq(String coord) throws BadCoordinatesException {
		Matcher m = PATTERN.matcher(coord);
		if (m.matches()) {
			Integer row = new Integer(m.group(2));
			Integer col = (new AlphaToIntHelper(m.group(1))).convert();
			
			return new Position2D(col, row);
		}
		else throw new BadCoordinatesException();
	}

}
