package it.escape.core.client.view.gui.maplabel;

public class AttackMarkManager extends MarkerManager {
	
	public AttackMarkManager() {
		super();
		setGraphics("resources/artwork/celle/fight.gif");
	}
	
	public void addFight(String location, MapViewer parent) {
		super.addMarker(location, parent);
	}

	public void clearFights(MapViewer parent) {
		super.clearMarkers(parent);
	}
	
}
