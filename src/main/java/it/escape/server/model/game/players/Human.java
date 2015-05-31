package it.escape.server.model.game.players;

import it.escape.server.model.game.exceptions.CardNotPresentException;
import it.escape.strings.StringRes;


public class Human extends Player {
	
	private final static int HUMANSMAXRANGE = 1;
	private final static int ADRENALINERANGE = 2;
	
	private boolean sedatives;
	
	private boolean escaped;
	
	public Human (String name) {
		super(name);
		escaped = false;
		maxRange = HUMANSMAXRANGE;
	}
	
	public PlayerTeams getTeam() {
		return PlayerTeams.HUMANS;
	}

	@Override
	public void startTurn() {
		hasMoved = false;
		hasAttacked = false;
		if (sedatives) {
			sedatives = false;
		}
		setMaxRange(HUMANSMAXRANGE);	//cleans up adrenaline
	}
	
	public boolean hasSedatives() {
		return sedatives;
	}
	
	public void setSedatives() {
		sedatives = true;
	}
	
	@Override
	public void setEscaped() {
		escaped = true;
	}
	
	public boolean hasEscaped() {
		return escaped;
	}
	
	public boolean hasCard(String cardName) {
		
		return false;
	}

	public void setAdrenaline() {
		setMaxRange(ADRENALINERANGE);
	}

	@Override
	public void die() {
		try {
			drawCard(StringRes.getString("cardKeys.defense"));
			log.fine(StringRes.getString("logging.misc.defendedMyself"));
		} catch (CardNotPresentException e) {
			setAlive(false);
		}
		
	}
}