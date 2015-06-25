package it.escape.core.client.view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class TeamVictoryPanel extends JPanel {

	private static final long serialVersionUID = 5L;

	private GridBagConstraints teamPanelconstraints;
	
	private JLabel label1team;
	private JLabel label2winners;
	private JLabel label3losers;
	private JTextArea teamStatusArea;
	private JTextArea winnersArea;
	private JTextArea losersArea;
	

	public TeamVictoryPanel() {
		setLayout(new GridBagLayout());
		teamPanelconstraints = new GridBagConstraints();
	}
	
	public void initializeTeamPanel(String teamName) {
		label1team = new JLabel(teamName+": ");
		teamPanelconstraints.gridx=0;
		teamPanelconstraints.gridy=0;
		add(label1team,teamPanelconstraints);
		
		teamStatusArea = new JTextArea();
		teamStatusArea.setEditable(false);
		teamPanelconstraints.gridx=1;
		teamPanelconstraints.gridy=0;
		add(teamStatusArea,teamPanelconstraints);
		
		label2winners = new JLabel("Winners: ");
		teamPanelconstraints.gridx=0;
		teamPanelconstraints.gridy=1;
		teamPanelconstraints.gridwidth=2;
		add(label2winners,teamPanelconstraints);
		
		winnersArea = new JTextArea();
		winnersArea.setEditable(false);
		winnersArea.setPreferredSize(new Dimension (100,30));
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
