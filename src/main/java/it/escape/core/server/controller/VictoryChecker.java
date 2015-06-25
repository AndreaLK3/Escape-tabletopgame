package it.escape.core.server.controller;

import it.escape.core.server.model.game.players.Alien;
import it.escape.core.server.model.game.players.Human;
import it.escape.core.server.model.game.players.Player;

import java.util.ArrayList;
import java.util.List;

public class VictoryChecker {
	
	private List<Player> players;
	
	private List<Human> humans;
	
	private List<Alien> aliens;
	
	private boolean outOfTurns;

	public VictoryChecker(List<Player> players) {
		this.players = players;
		outOfTurns = false;
		humans = new ArrayList<Human>();
		aliens = new ArrayList<Alien>();
		for (Player p : this.players) {
			if (p instanceof Human) {
				humans.add((Human)p);
			} else if (p instanceof Alien) {
				aliens.add((Alien)p);
			}
		}
	}
	
	public boolean isWinByEscape() {
		int positiveMatches = 0;
		
		for (Human h : humans) {
			if (!h.isAlive()) {
				positiveMatches++;
			} else if (h.hasEscaped()) {
				positiveMatches++;
			}
		}
		if (positiveMatches == humans.size()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Not really used in the game.
	 * Returns true if all the aliens are dead.
	 * @return
	 */
	public boolean isWinByAlienVanquished() {
		int positiveMatches = 0;
		
		for (Alien a : aliens) {
			if (!a.isAlive()) {
				positiveMatches++;
			}
		}
		if (positiveMatches == aliens.size()) {
			return true;
		}
		return false;
	}
	
	/**
	 * determine if the current game condition is a "victory condition":
	 * 1) all humans must be either dead or escaped
	 * @return
	 */
	public boolean isVictoryCondition() {
		return isWinByEscape();
	}
	
	/** This method checks if all the players of a team have disconnected. 
	 * (If this is the case, GameMaster will terminate the game).*/
	public boolean entireTeamDisconnected() {
		if (allAliensDisconnected() || allHumansDisconnected()) {
			return true;
		}
		return false;
	}
	
	public boolean allHumansDisconnected() {
		int counterHumans=0;
		for (Human h : humans) {
			if (h.isUserIdle()) {
				counterHumans++;
			}
		}	
		if (counterHumans==humans.size()) {
			return true;
		}
		return false;
	}
	
	public boolean allAliensDisconnected() {
		int counterAliens=0;
		for (Alien a : aliens) {
			if (a.isUserIdle()) {
				counterAliens++;
			}
		}	
		if (counterAliens==aliens.size()) {
			return true;
		}
		return false;
	}
	
	/**
	 * the entire alien team is considered winner if at least
	 * one human was killed
	 * @return
	 */
	public boolean allAliensWin() {
		for (Human h : humans) {
			if (!h.isAlive() || h.isUserIdle()) {
				return true;
			} 
		}
		return false;
	}
	
	/**
	 * check if there is at least one human winner
	 * @return
	 */
	public boolean areThereHumanWinners() {
		if (getHumanWinners().size() > 0) return true;
		return false;
	}
	
	public boolean allHumansEscaped() {
		int positiveMatches = 0;
		for (Human h : humans) {
			if (h.hasEscaped()) {
				positiveMatches++;
			}
		}
		if (positiveMatches == humans.size()) {
			return true;
		}
		return false;
	}
	
	/**
	 * the entire human team wins if every single human
	 * manages to escape OR all the aliens are dead
	 * @return
	 */
	public boolean allHumansWin() {
		return allHumansEscaped();
	}
	
	/**
	 * return a list of the human winners (can be an empty list)
	 * @return
	 */
	public List<Player> getHumanWinners() {
		if (allHumansWin() || allAliensDisconnected()) {  // add all the humans to the winners
			List<Player> ret = new ArrayList<Player>();
			for (Human h : humans) {
				ret.add(h);
			}
			return ret;
		} else {  // cherry-pick the human winners
			List<Player> ret = new ArrayList<Player>();
			for (Human h : humans) {
				if (h.hasEscaped()) {
					ret.add(h);
				}
			}
			return ret;
		}
	}

	public List<Player> getAlienWinners() {
		if (allAliensWin() || allHumansDisconnected() || isOutOfTurns()) {  // all aliens are winners
			List<Player> ret = new ArrayList<Player>();
			for (Alien a : aliens) {
				if (a.isAlive() && !a.isUserIdle()) {
					ret.add(a);
				}
			}
			return ret;
		} else {  // no alien winners
			return new ArrayList<Player>();
		}
	}

	public boolean isOutOfTurns() {
		return outOfTurns;
	}

	public void setOutOfTurns() {
		this.outOfTurns = true;
	}
}
