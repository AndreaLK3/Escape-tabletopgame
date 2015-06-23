package it.escape.core.client.view.gui;

import javax.swing.JTextField;

public class SimpleVisualTimer implements Runnable {
	
	private String formatString;
	
	private JTextField area;
	
	private final int startingETA;  // in seconds
	
	private final static int QUANTUM = 1000; // in milliseconds
	
	private int currentTime;  // in seconds
	
	private String currentText;
	
	private boolean running;

	public SimpleVisualTimer(JTextField area, String formatString, int startingETA) {
		this.area = area;
		this.formatString = formatString;
		this.startingETA = startingETA;
		currentTime = this.startingETA;
	}
	
	private void buildString() {
		currentText = String.format(formatString, currentTime);
	}
	
	private boolean unexpectedChange() {
		if (!area.getText().equals(currentText)) {
			return true;
		}
		return false;
	}
	
	private void clockTick() {
		currentTime -= (QUANTUM / 1000);
	}
	
	private void setNewText() {
		buildString();
		area.setText(currentText);
	}

	@Override
	public void run() {
		running = true;
		setNewText();
		while (running) {
			try {
				Thread.sleep(QUANTUM);
			} catch (InterruptedException e) {
			}
			clockTick();
			if (unexpectedChange()) {
				running = false;
			} else {
				if (currentTime <= 0) {
					running = false;
				} else {
					setNewText();
				}
			}
		}
		
	}
	
	
}
