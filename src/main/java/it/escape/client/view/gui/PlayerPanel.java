package it.escape.client.view.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PlayerPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private GridBagConstraints constraints = new GridBagConstraints();
	private JTextField playerArea;
	private JTextField statusArea;
	private JTextField lastKnownActionArea;
	
	public PlayerPanel () {
		this.setLayout(new GridBagLayout());
	
		playerArea = new JTextField();
		playerArea.setEditable(false);
		playerArea.setText("Unknown");
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		this.add(playerArea, constraints);
		
		statusArea = new JTextField();
		statusArea.setEditable(false);
		statusArea.setText("Unknown");
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 0.3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		this.add(statusArea, constraints);
		
		lastKnownActionArea = new JTextField();
		lastKnownActionArea.setEditable(false);
		lastKnownActionArea.setText("Unknown");
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.weightx = 0.3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		this.add(lastKnownActionArea, constraints);
	}
	
	

	//Update methods for the text areas
	public void updatePlayerArea (String s) {
		playerArea.setText(s);
	}
	
	public void updateStatusArea (String s) {
		statusArea.setText(s);
	}

	public void updateLastKnownActionArea (String s) {
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
