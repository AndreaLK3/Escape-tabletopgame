package it.escape.server.model.game.gamemap;

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

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "Position2D=[" + x.toString() + "," + y.toString() + "]";
	}
}
