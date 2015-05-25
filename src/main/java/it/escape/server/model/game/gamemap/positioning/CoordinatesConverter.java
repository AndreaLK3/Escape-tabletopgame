package it.escape.server.model.game.gamemap.positioning;

import it.escape.server.model.game.exceptions.BadCoordinatesException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Questa classe contiene varie funzioni statiche utili alla trasformazione
 * tra vari tipi di coordinate: cubiche (es.(1,2,-3)), alfanumeriche(B,2), bidimensionali (2,2).
 * n: le coordinate cubiche usano 3 assi, e sono tali che la somma delle coordinate in una posizione è sempre 0.
 * @author andrea
 */
public class CoordinatesConverter {
	
	// regex che corrisponde a una stringa costruita così: <lettere maiuscole><numeri>
	public static final Pattern PATTERN_MATCH = Pattern.compile("([A-Z]+)([0-9]+)");
	
	public static final Pattern PATTERN_DOPRETTIFY = Pattern.compile("([A-Z]+)([0-9])");
	
	/**
	 * 
	 * @param posC , posizione in coordinate cubiche, es: (1,3,4)
	 * @return nuovo oggetto Position2D, posizione con coordinate bidimensionali, es: (2,12)
	 */
	public static Position2D fromCubicToOddQ(PositionCubic posC) {
		Integer col;
		Integer row;
		
		col = posC.getX();
		row = ( posC.getZ() + (posC.getX() - (posC.getX()&1)) / 2 );
		
		return new Position2D(col, row);
	}
	
	/**
	 * convert "A1" -> "A01", and so on
	 * @param coord alphanumeric coordinates string
	 * @return
	 */
	public static String prettifyAlphaNum(String coord) {
		Matcher m = PATTERN_DOPRETTIFY.matcher(coord);
		if (m.matches()) {
			return m.group(1) + "0" + m.group(2);
		}
		else {
			return coord;
		}
	}
	
	/**
	 * @param posC, posizione in coordinate cubiche, es. (1,3,4)
	 * @return Stringa contenente riga e colonna, es: B12
	 */
	public static String fromCubicToAlphaNum(PositionCubic posC) {
		
		Position2D mypos = fromCubicToOddQ(posC);
		
		Character col = new Character((char) ('A' + mypos.getX()));
		
		return prettifyAlphaNum(new String(col.toString() + mypos.getY()));
	}
	
	/**
	 * 
	 * @param coordstring stringa del tipo "AAA000"
	 * @return Una nuova posizione con coordinate cubiche
	 */
	public static PositionCubic fromAlphaNumToCubic(String coordstring) throws BadCoordinatesException {
		Position2D coord = fromAlphaNumToOddq(coordstring);
		PositionCubic cubic = fromOddqToCubic (coord);
		return cubic;
	}
	
	public static PositionCubic fromOddqToCubic (Position2D Pos2D) {
		int x, y, z;
		
		Integer col = Pos2D.getX();
		Integer row = Pos2D.getY();
		
		x = col;
		y = row - ((col - (col&1)) / 2);
		z = -x - y;
		return new PositionCubic (x, y, z);
		
	}
	
	
	/**
	 * @param coord, stringa alfanumerica, es: B12
	 * @return nuovo oggetto del tipo Position2D con coordinate bidimensionali, es: (2,12)
	 * @throws BadCoordinatesException
	 */
	public static Position2D fromAlphaNumToOddq(String coord) throws BadCoordinatesException {
		Matcher m = PATTERN_MATCH.matcher(coord);
		if (m.matches()) {
			Integer row = new Integer(m.group(2));
			Integer col = (new AlphaToIntHelper(m.group(1))).convert();
			
			return new Position2D(col, row);
		}
		else throw new BadCoordinatesException();
	}

}
