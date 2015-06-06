package it.escape.client.Graphics;

import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.gamemap.loader.MapLoader;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.gamemap.positioning.Position2D;
import it.escape.utils.FilesHelper;
import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.model.game.gamemap.DangerousCell;
import it.escape.server.model.game.gamemap.EscapeCell;
import it.escape.server.model.game.gamemap.SafeCell;
import it.escape.server.model.game.gamemap.StartingCell;
import it.escape.server.model.game.players.PlayerTeams;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MapViewer extends JLabel {
	
	private int cellWidth = 62;
	
	private int cellHeight = 54;
	
	private int backgroundTileSize = 31;

	private static final long serialVersionUID = 1L;
	
	private int totalWidth;
	
	private int totalHeight;
	
	private MapLoader map;
	
	private Image background = null;
	
	public MapViewer(int cellWidth, int cellHeight) throws BadJsonFileException, IOException {
		super();
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		initialize();
		drawCells();
	}
	
	public MapViewer() throws BadJsonFileException, IOException {
		super();
		initialize();
		drawCells();
	}
	
	private void initialize() throws BadJsonFileException, IOException {
		background = ImageScaler.resizeImage("resources/artwork/map-background.png", backgroundTileSize, backgroundTileSize);
		map = new MapLoader(FilesHelper.getResourceFile("resources/Galilei.json"));
		Position2D mapsize = map.getMapSize();
		int max[] = cellToPixels(mapsize);
		totalWidth = max[0] + cellWidth;
		totalHeight = max[1] + (int) Math.ceil((cellHeight * 3)/2);
		setPreferredSize(new Dimension(totalWidth, totalHeight));
		setOpaque(true);
	}
	
	private void drawCells() {
		int escapeFlag = 1;
		for (Cell c : map) {
			Position2D pos2d = CoordinatesConverter.fromCubicToOddQ(c.getPosition());
			int coord[] = cellToPixels(pos2d);
			if (c instanceof SafeCell) {
				drawSafeCell(coord[0], coord[1], CoordinatesConverter.fromCubicToAlphaNum(c.getPosition()));
			} else if (c instanceof DangerousCell) {
				drawDangerousCell(coord[0], coord[1], CoordinatesConverter.fromCubicToAlphaNum(c.getPosition()));
			} else if (c instanceof EscapeCell) {
				drawEscapeCell(coord[0], coord[1], "" + (escapeFlag++));
			} else if (c instanceof StartingCell) {
				StartingCell s = (StartingCell) c;
				if (s.getType() == PlayerTeams.ALIENS) {
					drawAlienStartingCell(coord[0], coord[1]);
				} else if (s.getType() == PlayerTeams.HUMANS) {
					drawHumanStartingCell(coord[0], coord[1]);
				}
			} 
		}
	}
	
	private void drawAlienStartingCell(int x, int y) {
		JLabel label = new JLabel();
		drawCell(x,y,label,"resources/artwork/celle/partenza-alieni.png");
	}
	
	private void drawHumanStartingCell(int x, int y) {
		JLabel label = new JLabel();
		drawCell(x,y,label,"resources/artwork/celle/partenza-umani.png");
	}
	
	private void drawEscapeCell(int x, int y, String name) {
		JLabel label = new JLabel(name);
		label.setForeground(Color.WHITE);
		drawCell(x,y,label,"resources/artwork/celle/scialuppa.png");
	}
	
	private void drawDangerousCell(int x, int y, String name) {
		JLabel label = new JLabel(name);
		drawCell(x,y,label,"resources/artwork/celle/pericolosa.png");
	}
	
	private void drawSafeCell(int x, int y, String name) {
		JLabel label = new JLabel(name);
		drawCell(x,y,label,"resources/artwork/celle/sicura.png");
	}
	
	private void drawCell(int x, int y, JLabel label, String image) {
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
		ans[1] = (int) Math.round(size * Math.sqrt(3) * (row + 0.5 * (col&1))) - cellHeight;
		
		return ans;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g); 
	    if (background != null) {
	    	for (int x = 0; x < totalWidth; x += backgroundTileSize) {
	            for (int y = 0; y < totalHeight; y += backgroundTileSize) {
	            	g.drawImage(background, x, y, backgroundTileSize, backgroundTileSize,this);
	            }
	        }
	    }
	    	
	 }
}
