package it.escape.client.view.gui;

import it.escape.server.model.game.players.PlayerTeams;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TeamVictoryPanel extends JPanel {

	private GridBagConstraints constraints;
	
	private JLabel label1_team;
	private JLabel label2_winners;
	private JLabel label3_losers;
	private JTextArea teamStatusArea;
	private JTextArea winnersArea;
	private JTextArea losersArea;
	

	public TeamVictoryPanel() {
		setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
	}
	
	public void initializeTeamPanel(String teamName) {
		label1_team = new JLabel(teamName);
		constraints.gridx=0;
		constraints.gridy=0;
		add(label1_team,constraints);
		
		teamStatusArea = new JTextArea();
		constraints.gridx=1;
		constraints.gridy=0;
		add(teamStatusArea,constraints);
		
		label2_winners = new JLabel("Winners:");
		constraints.gridx=0;
		constraints.gridy=1;
		constraints.gridwidth=2;
		add(label2_winners,constraints);
		
		winnersArea = new JTextArea();
		constraints.gridx=0;
		constraints.gridy=2;
		constraints.gridwidth=2;
		add(winnersArea,constraints);
		
		label3_losers = new JLabel("Losers:");
		constraints.gridx=0;
		constraints.gridy=3;
		constraints.gridwidth=2;
		add(label3_losers,constraints);
		
		losersArea = new JTextArea();
		constraints.gridx=0;
		constraints.gridy=4;
		constraints.gridwidth=2;
		add(losersArea,constraints);
	}
	
	public void fillVictoryPanel(boolean teamDefeated, List<String> winners/*, List<String> losers*/) {
		String winnersString="";
		
		if (teamDefeated) {
			teamStatusArea.setText("Defeated");
		}
		for (String winnerName : winners) {
			winnersString = winnersString + winnerName + "\n";
		}
		winnersArea.setText(winnersString);
	}
	
}
