package it.escape.client.controller.gui;

import it.escape.client.view.gui.maplabel.MapCell;
import it.escape.client.view.gui.maplabel.MapViewer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class MouseOnMapCell extends MouseAdapter {
	
	private MapViewer mapview;
	
	public MouseOnMapCell(MapViewer mapview) {
		super();
		this.mapview = mapview;
	}

	public void mouseClicked(MouseEvent e) {  
		JOptionPane.showMessageDialog(null,
			    "You clicked on cell " + ((MapCell)e.getSource()).getCoord(),
			    "Click",
			    JOptionPane.PLAIN_MESSAGE);
    }
	
	public void mouseEntered(MouseEvent e) {
		MapCell source = (MapCell)e.getSource();
		mapview.highlightCell(source.getCoord());
    }
	
	public void mouseExited(MouseEvent e) {
		MapCell source = (MapCell)e.getSource();
		mapview.deHighlight();
    }
}
