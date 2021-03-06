package it.escape.core.server.model.game.gamemap.positioning;



/**
 * Classe immutabile usata per rappresentare la posizione di una cella esagonale.
 * Il sistema di coordinate usato fa riferimento a:
 * http://www.redblobgames.com/grids/hexagons/
 * nota che questa classe non fa nessun controllo che le coordinate appartengano
 * ad un certo "intervallo ammesso", dato che essa non ha conoscenza della mappa
 * complessiva.
 * @author michele
 *
 */
public final class PositionCubic{
	
	private final Integer x;
	private final Integer y;
	private final Integer z;
	
	/**
	 * basic constructor (cubic coordinates)
	 * @param x
	 * @param y
	 * @param z
	 */
	public PositionCubic(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
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

	public Position2D getOddQCoord() {
				
		return CoordinatesConverter.fromCubicToOddQ(this);
	}
	
	public String getAlphaNumCoord() {
		
		return CoordinatesConverter.fromCubicToAlphaNum(this);
	}
	
	@Override
	public int hashCode() {
		// genera un hash, accodando i 10 bit meno significativi di x,y,z
		// avanzano 2 bit in testa all'hash
		int a = x & 1023;
		int b = y & 1023;
		int c = z & 1023;
		return (a << 20) + (b << 10) + c;
	}
	
	/** This function verifies if 2 cubic positions/ sets of coordinates are equal
	 */
	@Override
	public boolean equals(Object o) {
		
		if (o == null || !(o instanceof PositionCubic)) {
			return false;
		}
		else {
			PositionCubic other = (PositionCubic) o;
			if (x.equals(other.getX()) && y.equals(other.getY()) && z.equals(other.getZ())) {
				return true;
			}
			else return false;
		}
	}
	
	public PositionCubic cubeAdd(PositionCubic addend) {
		return new PositionCubic(
				x + addend.getX(),
				y + addend.getY(),
				z + addend.getZ());
	}
	
	public int distanceFrom(PositionCubic other) {
		return (Math.abs(this.x - other.x) + Math.abs(this.y - other.y) + Math.abs(this.z - other.z)) / 2;
	}
	
	@Override
	public String toString() {
		return "PositionCubic=[" + x.toString() + "," + y.toString() + "," + z.toString() + "]";
	}
}
