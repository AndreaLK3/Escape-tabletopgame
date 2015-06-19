package it.escape.client.view.gui;

import static org.junit.Assert.*;
import it.escape.client.model.ModelForGUI;
import it.escape.client.model.VictoryState;
import it.escape.launcher.StartMenu;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.junit.Test;

public class TeamVictoryPanelTest {

	/**This graphic test must be run using the Debugging tool*/
	@Test
	public void test() {
		JFrame frame = new JFrame("Test victory panel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		frame.setSize(950, 600);
		frame.setVisible(true);
		createResultsPanel();
		spawnVictoryRecap();
		lastAction();
		
	}
	
	

	/**
	 * Spawn a dialog / panel / whatever to show the results of the match
	 * The dialog is spawned from inside the EDT, so that the application
	 * won't instantly stop.
	 * The model is passed as an argument
	 */
	public void spawnVictoryRecap() {
		EventQueue.invokeLater(
			new Runnable() {
				public void run() {
					JPanel resultsPanel = createResultsPanel();
						try {
						StartMenu.detectTextOnlyEnvironment();
						JOptionPane.showMessageDialog
						(null, resultsPanel, "End of the Game! Here are the results", JOptionPane.INFORMATION_MESSAGE);
						} 
						catch (HeadlessException e) {
						}
				} 
			});
	}
	
	/**This method creates panels with the final results for each team, depending on the results in VictoryState*/
	public JPanel createResultsPanel() {
		JPanel resultsPanel = new JPanel();
		VictoryState finalGameState= new VictoryState();
		GridBagConstraints panelConstraints = new GridBagConstraints();
		
		TeamVictoryPanel panelHumans = new TeamVictoryPanel();
		panelHumans.initializeTeamPanel("Humans");
		panelHumans.fillVictoryPanel(false, Arrays.asList("Alice", "Bob", "Charles"));
		
		TeamVictoryPanel panelAliens = new TeamVictoryPanel();
		panelAliens.initializeTeamPanel("Aliens");
		panelAliens.fillVictoryPanel(true, new ArrayList<String>());
		
		resultsPanel.setLayout(new GridBagLayout());
		panelConstraints.gridx=0;
		panelConstraints.gridy=0;
		panelConstraints.fill=GridBagConstraints.NONE;
		resultsPanel.add(panelHumans);
		
		panelConstraints.gridx=1;
		panelConstraints.gridy=0;
		panelConstraints.fill=GridBagConstraints.NONE;
		resultsPanel.add(panelAliens);
		return resultsPanel;
	}

	private int lastAction() {
		int x = 2+2;
		return x;
	}
}
