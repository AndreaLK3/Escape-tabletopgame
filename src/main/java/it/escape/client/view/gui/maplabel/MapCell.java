package it.escape.client.view.gui.maplabel;

import javax.swing.JLabel;

/**
 * Label representing a cell on the map, it's no different from
 * a vanilla JLabel, *but* it also stores its alfanum coordinates
 * @author michele
 *
 */
public class MapCell extends JLabel {

	private static final long serialVersionUID = 1L;

	private String alfaNumCoord;

	public MapCell(String name) {
		super(name);
	}

	public MapCell() {
		super();
	}

	public String getCoord() {
		return alfaNumCoord;
	}

	public void setCoord(String alfaNumCoord) {
		this.alfaNumCoord = alfaNumCoord;
	}

}
