package it.escape.client.Graphics;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PlayerStatusPanel extends JPanel {

	private JTextArea playerArea;
	private JTextArea statusArea;
	private JTextArea lastNoiseArea;
	
	public PlayerStatusPanel () {
		this.setLayout(new BorderLayout());
	
		playerArea = new JTextArea();
		playerArea.setEditable(false);
		playerArea.setText("Giocatore 1\n");
		playerArea.setVisible(true);
		this.add(playerArea, BorderLayout.WEST);

		statusArea = new JTextArea();
		statusArea.setEditable(false);
		statusArea.setText("Alive\n");
		this.add(statusArea ,BorderLayout.CENTER);

		lastNoiseArea = new JTextArea();
		lastNoiseArea.setEditable(false);
		lastNoiseArea.setText("C5\n");
		this.add(lastNoiseArea, BorderLayout.EAST);
	
	}
	
	public void updatePlayerArea () {
		
	}
	
	public void updateStatusArea () {
		
	}

	public void updateLastNoiseArea () {
	
	}
}
