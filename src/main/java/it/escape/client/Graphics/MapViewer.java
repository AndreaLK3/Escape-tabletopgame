package it.escape.client.Graphics;

import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.gamemap.loader.MapLoader;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.gamemap.positioning.Position2D;
import it.escape.utils.FilesHelper;
import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.model.game.gamemap.DangerousCell;
import it.escape.server.model.game.gamemap.SafeCell;

import java.awt.Dimension;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MapViewer extends JLabel {
	
	private int cellWidth = 62;
	
	private int cellHeight = 54;

	private static final long serialVersionUID = 1L;
	
	private int totalWidth;
	
	private int totalHeight;
	
	private MapLoader map;
	
	public MapViewer(int cellWidth, int cellHeight) throws BadJsonFileException, IOException {
		super();
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		map = new MapLoader(FilesHelper.getResourceFile("resources/Galilei.json"));
		initialize();
		drawCells();
	}
	
	public MapViewer() throws BadJsonFileException, IOException {
		super();
		map = new MapLoader(FilesHelper.getResourceFile("resources/Galilei.json"));
		initialize();
		drawCells();
	}
	
	private void initialize() {
		Position2D mapsize = map.getMapSize();
		int max[] = cellToPixels(mapsize);
		totalWidth = max[0] + cellWidth;
		totalHeight = max[1] + cellHeight;
		setPreferredSize(new Dimension(totalWidth, totalHeight));
	}
	
	private void drawCells() {
		for (Cell c : map) {
			Position2D pos2d = CoordinatesConverter.fromCubicToOddQ(c.getPosition());
			int coord[] = cellToPixels(pos2d);
			if (c instanceof SafeCell) {
				drawSafeCell(coord[0], coord[1], CoordinatesConverter.fromCubicToAlphaNum(c.getPosition()));
			} else if (c instanceof DangerousCell) {
				drawDangerousCell(coord[0], coord[1], CoordinatesConverter.fromCubicToAlphaNum(c.getPosition()));
			}
			
		}
	}
	
	private void drawDangerousCell(int x, int y, String name) {
		drawCell(x,y,name,"resources/artwork/celle/pericolosa.png");
	}
	
	private void drawSafeCell(int x, int y, String name) {
		drawCell(x,y,name,"resources/artwork/celle/sicura.png");
	}
	
	private void drawCell(int x, int y, String name, String image) {
		JLabel label = new JLabel(name);
		label.setPreferredSize(new Dimension(cellWidth, cellHeight));
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setIcon(new ImageIcon(ImageScaler.resizeImage(image, cellWidth, cellHeight)));
		placeAbsolute(x, y, label);
	}
	
	private void placeAbsolute(int x, int y, JLabel label) {
		add(label);
		label.setOpaque(false);
		Insets insets = getInsets();
		Dimension size = label.getPreferredSize();
		label.setBounds(
				x + insets.left,
				y + insets.top,
	            size.width,
	            size.height);
	}
	
	private int[] cellToPixels(Position2D posCella) {
		int ans[] = new int[2];
		int size = cellWidth/2;
		int row = posCella.getRow();
		int col = posCella.getCol();
		
		ans[0] = (int) Math.round(size * 3/2 * col);
		ans[1] = (int) Math.round(size * Math.sqrt(3) * (row + 0.5 * (col&1)));
		
		return ans;
	}
}
