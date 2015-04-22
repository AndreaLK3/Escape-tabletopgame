package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.gamemap.exceptions.BadCoordinatesException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PositionCubic {
	
	private final Integer x;
	private final Integer y;
	private final Integer z;
	
	// regex che corrisponde a una stringa costruita così: <lettere maiuscole><numeri>
	public static final Pattern PATTERN = Pattern.compile("([A-Z]+)([0-9]+)");
	
	public PositionCubic(Integer x, Integer y, Integer z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Integer getX() {
		return new Integer(x);
	}

	public Integer getY() {
		return new Integer(y);
	}

	public Integer getZ() {
		return new Integer(z);
	}

	private Position2D getOddQCoord(Position2D center) {
		Integer col;
		Integer row;
		
		col = x + center.getX();
		row = ( z + (x - (x&1)) / 2 ) + center.getY();
		
		return new Position2D(col, row);
	}
	
	public String getAphaNumCoord(Position2D center) {
		Position2D mypos = getOddQCoord(center);
		Character col = new Character((char) ('A' + mypos.getX()));
		
		return new String(col.toString() + mypos.getY().toString());
	}
	
	private static PositionCubic fromOddqCoord(Position2D coord, Position2D center) {
		Integer col = coord.getX() - center.getX();
		Integer row = coord.getY() - center.getY();
		
		Integer x,y,z;
		x = col;
		y = row - (col - (col&1)) / 2;
		z = -x - y;
		
		return new PositionCubic(x, y, z);
	}
	
	public static PositionCubic fromAlphaNumCoord(String coord, Position2D center) 
			throws BadCoordinatesException {
		Matcher m = PATTERN.matcher(coord);
		if (m.matches()) {
			Integer row = new Integer(m.group(2));
			Integer col = (new AlphaToIntHelper(m.group(1))).convert();
			System.out.println(col + ":" + row);
			
			return fromOddqCoord(new Position2D(col, row), center);
		}
		else throw new BadCoordinatesException();
	}
	
	public boolean equals(PositionCubic other) {
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
