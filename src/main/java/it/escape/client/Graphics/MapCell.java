package it.escape.client.Graphics;

import javax.swing.JLabel;

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
