package it.escape.client.view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
		/*constraints.gridx=0;
		constraints.weightx=1;
		this.add(playerArea, constraints);*/
		this.add(playerArea);
		
		statusArea = new JTextArea();
		statusArea.setEditable(false);
		statusArea.setText("Unknown");
		/*constraints.gridx=1;
		constraints.weightx=1;
		this.add(statusArea, constraints);*/
		this.add(statusArea);
		
		lastKnownActionArea = new JTextArea();
		lastKnownActionArea.setEditable(false);
		lastKnownActionArea.setText("Unknown");
		/*constraints.gridx=2;
		constraints.weightx=1;
		this.add(lastKnownActionArea, constraints);*/
		this.add(lastKnownActionArea);
	}
	
	
	/*private void setAreaWidth(JTextArea area,int minWidth, int maxWidth) {
		area.setMinimumSize(new Dimension(minWidth, area.getHeight()));
		area.setMaximumSize(new Dimension(maxWidth, area.getHeight()));
	}*/
	
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
