package it.escape.client.graphics;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PlayerPanel extends JPanel {

	private GridBagConstraints constraints = new GridBagConstraints();
	
	private JTextArea playerArea;
	private JTextArea statusArea;
	private JTextArea lastNoiseArea;
	
	public PlayerPanel () {
		this.setLayout(new GridBagLayout());
	
		playerArea = new JTextArea();
		playerArea.setEditable(false);
		playerArea.setText("Giocatore 1 \n");
		constraints.gridx=0;
		this.add(playerArea, constraints);

		statusArea = new JTextArea();
		statusArea.setEditable(false);
		statusArea.setText(" Alive\n");
		constraints.gridx=1;
		this.add(statusArea, constraints);

		lastNoiseArea = new JTextArea();
		lastNoiseArea.setEditable(false);
		lastNoiseArea.setText(" C5 \n");
		constraints.gridx=2;
		this.add(lastNoiseArea, constraints);
	
	}
	
	//Update methods for the text areas
	public void updatePlayerArea (String s) {
		playerArea.setText(s);
	}
	
	public void updateStatusArea (String s) {
		statusArea.setText(s);
	}

	public void updateLastNoiseArea (String s) {
		lastNoiseArea.setText(s);
	}
	
	
	//Getter methods for the Text Areas
	public String getPlayer () {
		return playerArea.getText();
	}
	
	public String getStatus () {
		return statusArea.getText();
	}
	
	public String getLastNoise () {
		return lastNoiseArea.getText();
	}
}
