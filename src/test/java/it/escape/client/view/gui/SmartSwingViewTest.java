package it.escape.client.view.gui;

import static org.junit.Assert.*;
import it.escape.client.model.ModelForGUI;

import org.junit.Before;
import org.junit.Test;

public class SmartSwingViewTest {

	ModelForGUI model;
	
	@Before
	public void setUpTest() {
		model.updateNowPlaying("Alice");
		model.updateNowPlaying("Bob");
		model.updateNowPlaying("Charles");
	}
	
	@Test
	public void testSpawnVictoryRecap() {
		fail("Not yet implemented");
	}

}
