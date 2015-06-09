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
		markers.add(nuova);
		parent.visualizeAndPlace(location, nuova);
	}
	
	public void clearNoises(MapViewer parent) {
		List<JLabel> temp = new ArrayList<JLabel>(markers);
		for (JLabel l : temp) {
			parent.remove(l);
			markers.remove(l);
		}
	}
}
