package it.escape.core.client.controller.gui;

import it.escape.core.client.controller.Relay;
import it.escape.core.client.view.gui.maplabel.MapCell;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClickSendPositionListener extends MouseAdapter {
	
	private Relay relay;
	
	private UpdaterSwingToViewInterface view;
	
	public ClickSendPositionListener(Relay relay, UpdaterSwingToViewInterface view) {
		super();
		this.relay = relay;
		this.view = view;
	}

	public void mouseClicked(MouseEvent e) {  
		String coordinates = ((MapCell)e.getSource()).getCoord();
		relay.relayMessage(coordinates);
		view.unbindPositionSender(this);
    }

	
}
