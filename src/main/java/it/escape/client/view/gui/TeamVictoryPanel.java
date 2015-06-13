package it.escape.client.view.gui;

import it.escape.server.model.game.players.PlayerTeams;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class TeamVictoryPanel extends JPanel {

	private GridBagConstraints teamPanelconstraints;
	
	private JLabel label1_team;
	private JLabel label2_winners;
	private JLabel label3_losers;
	private JTextArea teamStatusArea;
	private JTextArea winnersArea;
	private JTextArea losersArea;
	

	public TeamVictoryPanel() {
		setLayout(new GridBagLayout());
		teamPanelconstraints = new GridBagConstraints();
	}
	
	public void initializeTeamPanel(String teamName) {
		label1_team = new JLabel(teamName+": ");
		teamPanelconstraints.gridx=0;
		teamPanelconstraints.gridy=0;
		add(label1_team,teamPanelconstraints);
		
		teamStatusArea = new JTextArea();
		teamStatusArea.setEditable(false);
		teamPanelconstraints.gridx=1;
		teamPanelconstraints.gridy=0;
		add(teamStatusArea,teamPanelconstraints);
		
		label2_winners = new JLabel("Winners: ");
		teamPanelconstraints.gridx=0;
		teamPanelconstraints.gridy=1;
		teamPanelconstraints.gridwidth=2;
		add(label2_winners,teamPanelconstraints);
		
		winnersArea = new JTextArea();
		winnersArea.setEditable(false);
		teamPanelconstraints.gridx=0;
		teamPanelconstraints.gridy=2;
		teamPanelconstraints.gridwidth=2;
		add(winnersArea,teamPanelconstraints);
		
		this.setBorder(new LineBorder(new Color(180,180,240), 15));
		
	}
	
	public void fillVictoryPanel(boolean teamDefeated, List<String> winners) {
		String winnersString="";
		
		if (teamDefeated) {
			teamStatusArea.setText(" Defeated. ");
		}
		else {
			teamStatusArea.setText(" Not Defeated! ");
		}
		for (String winnerName : winners) {
			winnersString = winnersString + winnerName + "\n";
		}
		winnersArea.setText(winnersString);
	}
	
}
