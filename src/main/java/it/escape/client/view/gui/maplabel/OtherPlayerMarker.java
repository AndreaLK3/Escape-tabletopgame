package it.escape.client.view.gui.maplabel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

public class OtherPlayerMarker extends MarkerManager {
	
	public OtherPlayerMarker() {
		super();
		setGraphics("resources/artwork/celle/foreign-player.gif");
	}
	
	public void addPlayer(String location, String name, MapViewer parent) {
		super.addMarker(location, parent);
		nuova.setToolTipText(name);
	}
	
	public void removeSpecificPlayer(String name, MapViewer parent) {
		List<JLabel> temp = new ArrayList<JLabel>(markers);
		for (JLabel l : temp) {
			if (l.getToolTipText().equals(name)) {
				removeSingleMarker(l, parent);
			}
		}
	}

	public void clearPlayers(MapViewer parent) {
		super.clearMarkers(parent);
	}
}
