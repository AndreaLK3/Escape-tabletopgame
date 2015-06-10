package it.escape.client.view.gui.maplabel;

import it.escape.client.view.gui.ImageScaler;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class OtherPlayerMarker extends MarkerManager {
	
	public OtherPlayerMarker(MapViewer parent) {
		super();
		setGraphics("resources/artwork/celle/foreign-player.png", parent);
	}
	
	public void addPlayer(String location, String name, MapViewer parent) {
		LOG.warning("Added player " + name + ", position: " + location + " on the map");
		super.addMarker(location, parent);
		nuova.setToolTipText(name);
	}
	
	private void setGraphics(String filename, MapViewer parent) {
			graphics = new ImageIcon(ImageScaler.resizeImage(filename, 
					parent.getCellWidth(), 
					parent.getCellHeight()));
	}
	
	@Override
	protected void setPositionCorrection(MapViewer parent) {
		correctX = 0;
		correctY = 0;
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
