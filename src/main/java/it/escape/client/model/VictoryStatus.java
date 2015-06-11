package it.escape.client.model;

import java.util.ArrayList;
import java.util.List;

public class VictoryStatus {
	
	private List<String> humanWinners;
	
	private List<String> alienWinners;
	
	private boolean aliensDefeated;
	
	private boolean humansDefeated;

	public VictoryStatus() {
		humanWinners = new ArrayList<String>();
		alienWinners = new ArrayList<String>();
		aliensDefeated = false;
		humansDefeated = false;
	}
	
	
}
