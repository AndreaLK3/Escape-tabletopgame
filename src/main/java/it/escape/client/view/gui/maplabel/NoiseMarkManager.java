package it.escape.client.view.gui.maplabel;


public class NoiseMarkManager extends MarkerManager {

	public NoiseMarkManager() {
		super();
		setGraphics("resources/artwork/celle/alert.gif");
	}
	
	public void addNoise(String location, MapViewer parent) {
		super.addMarker(location, parent);
	}

	public void clearNoises(MapViewer parent) {
		super.clearMarkers(parent);
	}
}
