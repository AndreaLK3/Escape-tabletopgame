package it.escape.client.controller.gui;

import it.escape.client.controller.Relay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class NameListener implements ActionListener {

	Relay relay;
	
	public NameListener(Relay relay) {
		this.relay = relay; 
	}
	
	public void actionPerformed(ActionEvent event) {
		relay.renameSelf(((JTextField)event.getSource()).getText());

	}

}
