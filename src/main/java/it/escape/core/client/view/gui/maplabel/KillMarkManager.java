package it.escape.core.client.view.gui.maplabel;

public class KillMarkManager extends MarkerManager {
	
	public KillMarkManager() {
		super();
		setGraphics("resources/artwork/celle/skull.gif");
	}
	
	public void addBones(String location, MapViewer parent) {
		super.addMarker(location, parent);
	}

	public void clearBones(MapViewer parent) {
		super.clearMarkers(parent);
	}
}
