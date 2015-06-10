package it.escape.client.view.gui.maplabel;

public class OtherPlayerMarker extends MarkerManager {
	
	public OtherPlayerMarker() {
		super();
		setGraphics("resources/artwork/celle/foreign-player.gif");
	}
	
	public void addPlayer(String location, String name, MapViewer parent) {
		super.addMarker(location, parent);
		nuova.setToolTipText(name);
	}

	public void clearPlayers(MapViewer parent) {
		super.clearMarkers(parent);
	}
}
