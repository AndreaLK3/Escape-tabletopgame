package it.escape.client.view.gui.maplabel;

import it.escape.utils.FilesHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class NoiseMarkManager {
	
	private List<JLabel> markers;
	
	private Icon noisy;

	public NoiseMarkManager() {
		markers = new ArrayList<JLabel>();
		try {
			noisy = new ImageIcon(FilesHelper.getResourceAsByteArray("resources/artwork/celle/alert.gif"));
		} catch (IOException e) {
		}
	}
	
	public void addNoise(String location, MapViewer parent) {
		JLabel nuova = new JLabel(noisy);
		int correctX = (parent.getCellWidth() - noisy.getIconWidth()) / 2;
		int correctY = (parent.getCellHeight() - noisy.getIconHeight()) / 2;
		markers.add(nuova);
		parent.add(nuova);
		parent.setComponentZOrder(nuova, parent.getNoiseZ());
		parent.visualizeAndPlace(location, nuova, correctX, correctY);
	}
	
	public void clearNoises(MapViewer parent) {
		List<JLabel> temp = new ArrayList<JLabel>(markers);
		for (JLabel l : temp) {
			parent.remove(l);
			markers.remove(l);
		}
	}
}
