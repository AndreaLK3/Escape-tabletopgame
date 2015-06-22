package it.escape.core.client.view.gui.maplabel;

public class ClosedHatchManager extends MarkerManager {
	
	public ClosedHatchManager() {
		super();
		setGraphics("resources/artwork/celle/pod-denied.png");
	}
	
	public void addClosedHatch(String location, MapViewer parent) {
		super.addMarker(location, parent);
	}
}
