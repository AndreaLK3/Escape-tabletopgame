package it.escape.server.model.game.gamemap.positioning;

/**
 * Classe (immutabile) usata internamente dalle altre classi per raccogliere
 * coordinate cartesiane
 */
public final class Position2D implements Cloneable {
	
	private final Integer col;
	
	private final Integer row;

	public Position2D(Integer column, Integer row) {
		this.col = column;
		this.row = row;
	}
	
	@Override
	public Position2D clone() {
		return new Position2D(col, row);
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	@Override
	public int hashCode() {
		int a = col & 65535;
		int b = row & 65535;
		return (a<<16) + (b);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Position2D && o!=null) {
			Position2D other = (Position2D)o;
			if (col == other.getCol() && row == other.getRow()) {
				return true;
				}
			else
				return false;
		}
		else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "Position2D=[" + col.toString() + "," + row.toString() + "]";
	}
}
