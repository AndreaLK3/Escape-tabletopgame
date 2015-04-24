package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.gamemap.exceptions.BadCoordinatesException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * tipo immutabile usato per rappresentare la posizione di una cella esagonale
 * il sistema di coordinate usato fa riferimento a:
 * http://www.redblobgames.com/grids/hexagons/
 * nota che questa classe non fa nessun controllo che le coordinate appartengano
 * ad un certo "intervallo ammesso", dato che essa non ha conoscenza della mappa
 * complessiva.
 * L'unica informazione extra da definire sono le coordinate odd-q del centro della
 * mappa, che sarà lo zero nel sistema di coordinate cubic 
 */

public final class PositionCubic {
	
	private final Integer x;
	private final Integer y;
	private final Integer z;
	
	// regex che corrisponde a una stringa costruita così: <lettere maiuscole><numeri>
	public static final Pattern PATTERN = Pattern.compile("([A-Z]+)([0-9]+)");
	
	/**
	 * costruttore di base (coordinate cubiche)
	 * @param x
	 * @param y
	 * @param z
	 */
	public PositionCubic(Integer x, Integer y, Integer z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * costruttore avanzato (usa le coordinate del gioco)
	 * @param coordstring stringa del tipo "AAA000"
	 * @throws BadCoordinatesException
	 */
	public PositionCubic(String coordstring) throws BadCoordinatesException {
		Position2D coord = AlphaToOddq(coordstring);
		Integer col = coord.getX();
		Integer row = coord.getY();
		
		x = col;
		y = row - ((col + (col&1)) / 2);
		z = -x - y;
	}
	
	/**
	 * costruttore avanzato (usa le coordinate odd-q)
	 * @param coord coordinate cartesiane
	 */
	public PositionCubic(Position2D coord) {
		Integer col = coord.getX();
		Integer row = coord.getY();
		
		x = col;
		y = row - ((col + (col&1)) / 2);
		z = -x - y;
	}
	
	private Position2D AlphaToOddq(String coord) throws BadCoordinatesException {
		Matcher m = PATTERN.matcher(coord);
		if (m.matches()) {
			Integer row = new Integer(m.group(2));
			Integer col = (new AlphaToIntHelper(m.group(1))).convert();
			
			return new Position2D(col, row);
		}
		else throw new BadCoordinatesException();
	}
	
	// qui sfrutto l'auto-unboxing del tipo Integer
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	private Position2D getOddQCoord() {
		Integer col;
		Integer row;
		
		col = x;
		row = ( z + (x - (x&1)) / 2 );
		
		return new Position2D(col, row);
	}
	
	public String getAphaNumCoord() {
		Position2D mypos = getOddQCoord();
		Character col = new Character((char) ('A' + mypos.getX()));
		
		return new String(col.toString() + mypos.getY());
	}
	
	public boolean equals(PositionCubic other) {
		// per verificare se due coordinate sono uguali
		if (x.equals(other.getX()) && y.equals(other.getY()) && z.equals(other.getZ())) {
			return true;
		}
		else return false;
	}
	
	@Override
	public String toString() {
		return "PositionCubic=[" + x.toString() + "," + y.toString() + "," + z.toString() + "]";
	}
}
