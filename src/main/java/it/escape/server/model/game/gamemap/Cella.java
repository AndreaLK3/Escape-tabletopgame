package it.escape.server.model.game.gamemap;

public class Cella {
	
	private PositionCubic position;
	
	private Settore tipoSettore;
	
	public PositionCubic getPosition() {
		return position;
	}
	
	public Cella(PositionCubic position, Settore tipoSettore) {
		this.position = position;
		this.tipoSettore = tipoSettore;	
	}
	
	

}
