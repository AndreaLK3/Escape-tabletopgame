package it.escape.client.graphics;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PlayerPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private GridBagConstraints constraints = new GridBagConstraints();
	
	private JTextArea playerArea;
	private JTextArea statusArea;
	private JTextArea lastKnownActionArea;
	
	public PlayerPanel () {
		this.setLayout(new GridBagLayout());
	
		playerArea = new JTextArea();
		playerArea.setEditable(false);
		playerArea.setText("Giocatore 1");
		constraints.gridx=0;
		this.add(playerArea, constraints);

		statusArea = new JTextArea();
		statusArea.setEditable(false);
		statusArea.setText(" Alive");
		constraints.gridx=1;
		this.add(statusArea, constraints);

		lastKnownActionArea = new JTextArea();
		lastKnownActionArea.setEditable(false);
		lastKnownActionArea.setText(" C5");
		constraints.gridx=2;
		this.add(lastKnownActionArea, constraints);
	
	}
	
	//Update methods for the text areas
	public void updatePlayerArea (String s) {
		playerArea.setText(s);
	}
	
	public void updateStatusArea (String s) {
		statusArea.setText(s);
	}

	public void updateLastNoiseArea (String s) {
		lastKnownActionArea.setText(s);
	}
	
	
	//Getter methods for the Text Areas
	public String getPlayer () {
		return playerArea.getText();
	}
	
	public String getStatus () {
		return statusArea.getText();
	}
	
	public String getLastKnownAction () {
		return lastKnownActionArea.getText();
	}
}
