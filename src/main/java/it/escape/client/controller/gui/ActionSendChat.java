package it.escape.client.controller.gui;

import it.escape.client.controller.Relay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class ActionSendChat implements ActionListener {

	private Relay relay;

	public ActionSendChat(Relay relay) {
		this.relay = relay;
	}
	
	public void actionPerformed(ActionEvent e) {
		JTextField source = (JTextField)e.getSource();
		relay.sendChat(source.getText());
		source.setText("");
	}

}
