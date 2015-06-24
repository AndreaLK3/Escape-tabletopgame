package it.escape.core.server.controller;

import static org.junit.Assert.*;
import it.escape.core.server.controller.VictoryChecker;
import it.escape.core.server.model.game.players.Alien;
import it.escape.core.server.model.game.players.Human;
import it.escape.core.server.model.game.players.Player;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class VictoryCheckerTest {
	
	private final String defaultName = "pippo";

	@Test
	public void testHumansWin() {
		VictoryChecker conditions = new VictoryChecker(testListHumansWin());
		assertEquals(true, conditions.allHumansWin());
		assertEquals(true, conditions.areThereHumanWinners());
		assertEquals(false, conditions.allAliensWin());
		assertEquals(true, conditions.isVictoryCondition());
		assertEquals(3,conditions.getHumanWinners().size());
		assertEquals(0,conditions.getAlienWinners().size());
	}
	
	@Test
	public void testAliensWin() {
		VictoryChecker conditions = new VictoryChecker(testListAliensWin());
		assertEquals(false, conditions.allHumansWin());
		assertEquals(false, conditions.areThereHumanWinners());
		assertEquals(true, conditions.allAliensWin());
		assertEquals(true, conditions.isVictoryCondition());
		assertEquals(0,conditions.getHumanWinners().size());
		assertEquals(3,conditions.getAlienWinners().size());
	}
	
	@Test
	public void testBothWin() {
		VictoryChecker conditions = new VictoryChecker(testListBothWin());
		assertEquals(false, conditions.allHumansWin());
		assertEquals(true, conditions.areThereHumanWinners());
		assertEquals(true, conditions.allAliensWin());
		assertEquals(true, conditions.isVictoryCondition());
		assertEquals(1,conditions.getHumanWinners().size());
		assertEquals(3,conditions.getAlienWinners().size());
	}
	
	@Test
	public void testBothWin2() {
		VictoryChecker conditions = new VictoryChecker(testListBothWin2());
		assertEquals(false, conditions.allHumansWin());
		assertEquals(true, conditions.areThereHumanWinners());
		assertEquals(true, conditions.allAliensWin());
		assertEquals(true, conditions.isVictoryCondition());
		assertEquals(2,conditions.getHumanWinners().size());
		assertEquals(3,conditions.getAlienWinners().size());
	}
	
	private List<Player> testListHumansWin() {
		List<Player> ret = new ArrayList<Player>();
		Human h;
		Alien a;
		h = new Human(defaultName);
		h.setEscaped();
		ret.add(h);
		a = new Alien(defaultName);
		ret.add(a);
		h = new Human(defaultName);
		h.setEscaped();
		ret.add(h);
		a = new Alien(defaultName);
		ret.add(a);
		h = new Human(defaultName);
		h.setEscaped();
		ret.add(h);
		a = new Alien(defaultName);
		ret.add(a);
		
		return ret;
	}

	private List<Player> testListAliensWin() {
		List<Player> ret = new ArrayList<Player>();
		Human h;
		Alien a;
		h = new Human(defaultName);
		h.setAlive(false);
		ret.add(h);
		a = new Alien(defaultName);
		ret.add(a);
		h = new Human(defaultName);
		h.setAlive(false);
		ret.add(h);
		a = new Alien(defaultName);
		ret.add(a);
		h = new Human(defaultName);
		h.setAlive(false);
		ret.add(h);
		a = new Alien(defaultName);
		ret.add(a);
		
		return ret;
	}
	
	private List<Player> testListBothWin() {
		List<Player> ret = new ArrayList<Player>();
		Human h;
		Alien a;
		h = new Human(defaultName);
		h.setAlive(false);
		ret.add(h);
		a = new Alien(defaultName);
		ret.add(a);
		h = new Human(defaultName);
		h.setEscaped();
		ret.add(h);
		a = new Alien(defaultName);
		ret.add(a);
		h = new Human(defaultName);
		h.setAlive(false);
		ret.add(h);
		a = new Alien(defaultName);
		ret.add(a);
		
		return ret;
	}
	
	private List<Player> testListBothWin2() {
		List<Player> ret = new ArrayList<Player>();
		Human h;
		Alien a;
		h = new Human(defaultName);
		h.setEscaped();
		ret.add(h);
		a = new Alien(defaultName);
		ret.add(a);
		h = new Human(defaultName);
		h.setEscaped();
		ret.add(h);
		a = new Alien(defaultName);
		ret.add(a);
		h = new Human(defaultName);
		h.setAlive(false);
		ret.add(h);
		a = new Alien(defaultName);
		ret.add(a);
		
		return ret;
	}
}
