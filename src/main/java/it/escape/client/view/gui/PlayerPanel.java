package it.escape.client.view.gui;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PlayerPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private GridBagConstraints constraints = new GridBagConstraints();
	private JTextArea playerArea;
	private JTextArea statusArea;
	private JTextArea lastKnownActionArea;
	
	public PlayerPanel () {
		this.setLayout(new GridLayout());
	
		playerArea = new JTextArea();
		playerArea.setEditable(false);
		playerArea.setText("Unknown");
		this.add(playerArea);
		
		statusArea = new JTextArea();
		statusArea.setEditable(false);
		statusArea.setText("Unknown");
		this.add(statusArea);
		
		lastKnownActionArea = new JTextArea();
		lastKnownActionArea.setEditable(false);
		lastKnownActionArea.setText("Unknown");
		this.add(lastKnownActionArea);
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
