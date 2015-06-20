package it.escape.core.client.controller.gui;

import it.escape.core.client.controller.Relay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JTextField;

/**This is a Listener on the JTextField that contains your name, 
 * that you can use to modify. 
 */
public class NameListener implements ActionListener, Serializable {

	private static final long serialVersionUID = 12L;
	
	Relay relay;
	
	public NameListener(Relay relay) {
		this.relay = relay; 
	}
	
	public void actionPerformed(ActionEvent event) {
		relay.renameSelf(((JTextField)event.getSource()).getText());

	}

}
