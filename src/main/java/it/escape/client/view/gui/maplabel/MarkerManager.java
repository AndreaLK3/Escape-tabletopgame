package it.escape.client.view.gui.maplabel;

import it.escape.utils.FilesHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class MarkerManager {
	
private List<JLabel> markers;
	
	protected Icon graphics;

	public MarkerManager() {
		markers = new ArrayList<JLabel>();
	}
	
	protected void setGraphics(String filename) {
		try {
			graphics = new ImageIcon(FilesHelper.getResourceAsByteArray(filename));
		} catch (IOException e) {
		}
	}
	
	protected void addMarker(String location, MapViewer parent) {
		JLabel nuova = new JLabel(graphics);
		int correctX = (parent.getCellWidth() - graphics.getIconWidth()) / 2;
		int correctY = (parent.getCellHeight() - graphics.getIconHeight()) / 2;
		markers.add(nuova);
		parent.add(nuova);
		parent.setComponentZOrder(nuova, parent.getNoiseZ());
		parent.visualizeAndPlace(location, nuova, correctX, correctY);
	}
	
	protected void clearMarkers(MapViewer parent) {
		List<JLabel> temp = new ArrayList<JLabel>(markers);
		for (JLabel l : temp) {
			parent.remove(l);
			markers.remove(l);
		}
	}
	
}
