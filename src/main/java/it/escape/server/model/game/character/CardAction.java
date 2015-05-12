package it.escape.server.model.game.character;

/** The set of actions a character does because a card told him to.*/
public interface CardAction {
	
	public void noiseInMySector();
	
	public void noiseInOtherSector();
	
	public void declareSilence();
}
