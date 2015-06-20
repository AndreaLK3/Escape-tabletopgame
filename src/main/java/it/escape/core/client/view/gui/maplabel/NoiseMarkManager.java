package it.escape.core.client.view.gui.maplabel;

import java.awt.EventQueue;


public class NoiseMarkManager extends MarkerManager {
	
	private static final int VANISH_DELAY = 2000;

	public NoiseMarkManager() {
		super();
		setGraphics("resources/artwork/celle/alert.gif");
	}
	
	public void addNoise(String location, MapViewer parent) {
		super.addMarker(location, parent);
	}
	
	/**
	 * The noises are deleted with VANISH_DELAY milliseconds
	 * of delay, so that you won't miss a single one
	 * @param parent
	 */
	public void clearNoises(final MapViewer parent) {
		final NoiseMarkManager caller = this;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Thread.sleep(VANISH_DELAY);
				} catch (InterruptedException e) {
				}
				caller.clearMarkers(parent);
			}
		});
		
	}
}
