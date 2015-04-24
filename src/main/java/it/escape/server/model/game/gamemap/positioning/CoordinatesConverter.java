package it.escape.server.model.game.gamemap.positioning;

import it.escape.server.model.game.gamemap.exceptions.BadCoordinatesException;

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
	public static final Pattern PATTERN = Pattern.compile("([A-Z]+)([0-9]+)");
	
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
	 * @param posC, posizione in coordinate cubiche, es. (1,3,4)
	 * @return Stringa contenente riga e colonna, es: B12
	 */
	public static String fromCubicToAlphaNum(PositionCubic posC) {
		
		Position2D mypos = fromCubicToOddQ(posC);
		
		Character col = new Character((char) ('A' + mypos.getX()));
		
		return new String(col.toString() + mypos.getY());
	}
	
	/**
	 * 
	 * @param coordstring stringa del tipo "AAA000"
	 * @throws BadCoordinatesException
	 * @return Una nuova posizione con coordinate cubiche
	 */
	public static PositionCubic fromAlphaNumToCubic(String coordstring) throws BadCoordinatesException {
		int x, y, z;
		
		Position2D coord = fromAlphaNumToOddq(coordstring);
		Integer col = coord.getX();
		Integer row = coord.getY();
		
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
		Matcher m = PATTERN.matcher(coord);
		if (m.matches()) {
			Integer row = new Integer(m.group(2));
			Integer col = (new AlphaToIntHelper(m.group(1))).convert();
			
			return new Position2D(col, row);
		}
		else throw new BadCoordinatesException();
	}

}
