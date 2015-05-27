package it.escape.server.controller;

import it.escape.server.model.game.players.Alien;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;

import java.util.ArrayList;
import java.util.List;

public class VictoryChecker {
	
	private List<Player> players;
	
	private List<Human> humans;
	
	private List<Alien> aliens;

	public VictoryChecker(List<Player> players) {
		this.players = players;
		for (Player p : players) {
			if (p instanceof Human) {
				humans.add((Human)p);
			} else if (p instanceof Alien) {
				aliens.add((Alien)p);
			}
		}
	}
	
	/**
	 * determine if the current game condition is a "victory condition":
	 * all humans must be either dead or escaped
	 * @return
	 */
	public boolean isVictoryCondition() {
		int positiveMatches = 0;
		
		for (Human h : humans) {
			if (!h.isAlive()) {
				positiveMatches++;
			} else if (h.getEscaped()) {
				positiveMatches++;
			}
		}
		
		if (positiveMatches == humans.size()) {
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
			if (!h.isAlive()) {
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
	
	/**
	 * the entire human team wins if every single human
	 * manages to escape
	 * @return
	 */
	public boolean allHumansWin() {
		int positiveMatches = 0;
		
		for (Human h : humans) {
			if (h.getEscaped()) {
				positiveMatches++;
			}
		}
		
		if (positiveMatches == humans.size()) {
			return true;
		}
		return false;
	}
	
	/**
	 * return a list of the human winners (can be an empty list)
	 * @return
	 */
	public List<Player> getHumanWinners() {
		List<Player> ret = new ArrayList<Player>();
		for (Human h : humans) {
			if (h.getEscaped()) {
				ret.add(h);
			}
		}
		return ret;
	}

	public List<Player> getAlienWinners() {
		if (allAliensWin()) {
			List<Player> ret = new ArrayList<Player>();
			for (Alien a : aliens) {
				ret.add(a);
			}
			return ret;
		} else {
			return new ArrayList<Player>();
		}
	}
}
