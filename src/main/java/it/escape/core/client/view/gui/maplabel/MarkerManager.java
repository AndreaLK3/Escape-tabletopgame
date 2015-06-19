package it.escape.core.client.view.gui.maplabel;

import it.escape.tools.utils.FilesHelper;
import it.escape.tools.utils.LogHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class MarkerManager {
	
	protected static final Logger LOG = Logger.getLogger( MarkerManager.class.getName() );
	
	protected List<JLabel> markers;
	
	protected Icon graphics;
	
	protected JLabel nuova;
	
	protected int correctX;
	protected int correctY;

	public MarkerManager() {
		LogHelper.setDefaultOptions(LOG);
		markers = new ArrayList<JLabel>();
	}
	
	protected void setGraphics(String filename) {
		try {
			graphics = new ImageIcon(FilesHelper.getResourceAsByteArray(filename));
		} catch (IOException e) {
			LOG.warning("Cannot load image " + filename);
		}
	}
	
	protected void setPositionCorrection(MapViewer parent) {
		correctX = (parent.getCellWidth() - graphics.getIconWidth()) / 2;
		correctY = (parent.getCellHeight() - graphics.getIconHeight()) / 2;
	}
	
	protected void addMarker(String location, MapViewer parent) {
		nuova = new JLabel(graphics);
		setPositionCorrection(parent);
		markers.add(nuova);
		parent.add(nuova);
		parent.setComponentZOrder(nuova, parent.getNoiseZ());
		parent.visualizeAndPlace(location, nuova, correctX, correctY);
	}
	
	protected void clearMarkers(MapViewer parent) {
		List<JLabel> temp = new ArrayList<JLabel>(markers);
		for (JLabel l : temp) {
			removeSingleMarker(l, parent);
		}
	}
	
	protected void removeSingleMarker(JLabel marker, MapViewer parent) {
		parent.remove(marker);
		markers.remove(marker);
	}
	
}
