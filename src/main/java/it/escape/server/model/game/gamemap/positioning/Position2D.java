package it.escape.server.model.game.gamemap.positioning;

/**
 * Classe (immutabile) usata internamente dalle altre classi per raccogliere
 * coordinate cartesiane
 */
public final class Position2D implements Cloneable {
	
	private final Integer x;
	
	private final Integer y;

	public Position2D(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public Position2D clone() {
		// l'ho messo solo perchè, essendo la classe immutabile, può servirci
		return new Position2D(x, y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		int a = x & 65535;
		int b = y & 65535;
		return (a<<16) + (b);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Position2D && o!=null) {
			Position2D other = (Position2D)o;
			if (x == other.getX() && y == other.getY()) {
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
		return "Position2D=[" + x.toString() + "," + y.toString() + "]";
	}
}
