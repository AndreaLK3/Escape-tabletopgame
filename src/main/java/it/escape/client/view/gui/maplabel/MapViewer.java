package it.escape.client.view.gui.maplabel;

import it.escape.client.view.gui.ImageScaler;
import it.escape.server.model.game.exceptions.BadCoordinatesException;
import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.model.game.gamemap.DangerousCell;
import it.escape.server.model.game.gamemap.EscapeCell;
import it.escape.server.model.game.gamemap.SafeCell;
import it.escape.server.model.game.gamemap.StartingCell;
import it.escape.server.model.game.gamemap.loader.MapLoader;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.gamemap.positioning.Position2D;
import it.escape.server.model.game.players.PlayerTeams;
import it.escape.strings.StringRes;
import it.escape.utils.FilesHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * A large jlabel containing smaller jlabels (the cells)
 * It offers an addCellListener(MouseListener) method; said
 * method will notify the MouseListener whenever a cell is
 * clicked.
 * Also note that differences between cell types are purely
 * esthetical here.
 * @author michele
 *
 */
public class MapViewer extends JLabel {
	
	private int cellWidth = 62;
	
	private int cellHeight = 54;
	
	private int backgroundTileSize = 31;

	private static final long serialVersionUID = 1L;
	
	private static final int cell_z = 0;
	
	private static final int noise_z = 2;
	
	private static final int overlays_z = 3;
	
	private int totalWidth;
	
	private int totalHeight;
	
	private MapLoader map;
	
	private Image background = null;
	
	private Icon playerHere;
	
	private Icon cellHighlight;
	
	private JLabel highlightOverlay;
	
	private JLabel playerHereOverlay;
	
	private MouseListener submitMouseAction;
	
	private List<MouseListener> cellListeners;
	
	private NoiseMarkManager noiseManager;
	
	private OtherPlayerMarker strangerManager;
	
	public MapViewer(int cellWidth, int cellHeight) throws BadJsonFileException, IOException {
		super();
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		initialize();
		initMouseListener();
		
	}
	
	public MapViewer() throws BadJsonFileException, IOException {
		super();
		initialize();
		initMouseListener();
	}
	
	public void setMap(String name, Runnable runAfterDraw) throws BadJsonFileException, IOException {
		map = new MapLoader(FilesHelper.getResourceFile("resources/" + name + ".json"));
		Position2D mapsize = map.getMapSize();
		int max[] = cellToPixels(mapsize);
		totalWidth = max[0] + cellWidth;
		totalHeight = max[1] + (int) Math.ceil((cellHeight * 3)/2);
		setPreferredSize(new Dimension(totalWidth, totalHeight));
		
		drawCells();
		setComponentZOrder(playerHereOverlay, overlays_z);
		setComponentZOrder(highlightOverlay, overlays_z);
		repaint();
		
		if (runAfterDraw != null) {
			new Thread(runAfterDraw).start();
		}
	}
	
	private void initialize() {
		noiseManager = new NoiseMarkManager();
		strangerManager = new OtherPlayerMarker();
		playerHere = new ImageIcon(ImageScaler.resizeImage("resources/artwork/celle/player-here.png", cellWidth, cellHeight));
		cellHighlight = new ImageIcon(ImageScaler.resizeImage("resources/artwork/celle/highlight.png", cellWidth, cellHeight));
		highlightOverlay = new JLabel(cellHighlight);
		highlightOverlay.setVisible(false);
		playerHereOverlay = new JLabel(playerHere);
		playerHereOverlay.setVisible(false);
		
		add(playerHereOverlay);
		add(highlightOverlay);
		
		background = ImageScaler.resizeImage("resources/artwork/map-background.png", backgroundTileSize, backgroundTileSize);
		totalWidth = 400;
		totalHeight = 400;
		setPreferredSize(new Dimension(totalWidth, totalHeight));
		setOpaque(true);
	}
	
	private void initMouseListener() {
		cellListeners = new ArrayList<MouseListener>();
		submitMouseAction = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				notifyClick(e);
		    }
			public void mouseEntered(MouseEvent e) {
				notifyHover(e);
			}
			public void mouseExited(MouseEvent e) {
				notifyLeave(e);
			}
		};
	}
	
	private void notifyClick(MouseEvent e) {
		List<MouseListener> temp = new ArrayList<MouseListener>(cellListeners);
		for(MouseListener ml: temp){
		    ml.mouseClicked(e);
		}
	}
	
	private void notifyHover(MouseEvent e) {
		List<MouseListener> temp = new ArrayList<MouseListener>(cellListeners);
		for(MouseListener ml: temp){
		    ml.mouseEntered(e);
		}
	}
	
	private void notifyLeave(MouseEvent e) {
		List<MouseListener> temp = new ArrayList<MouseListener>(cellListeners);
		for(MouseListener ml: temp){
		    ml.mouseExited(e);
		}
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
				drawEscapeCell(coord[0], coord[1], "" + (escapeFlag++), CoordinatesConverter.fromCubicToAlphaNum(c.getPosition()));
			} else if (c instanceof StartingCell) {
				StartingCell s = (StartingCell) c;
				if (s.getType() == PlayerTeams.ALIENS) {
					drawAlienStartingCell(coord[0], coord[1], CoordinatesConverter.fromCubicToAlphaNum(c.getPosition()));
				} else if (s.getType() == PlayerTeams.HUMANS) {
					drawHumanStartingCell(coord[0], coord[1], CoordinatesConverter.fromCubicToAlphaNum(c.getPosition()));
				}
			} 
		}
	}
	
	private void drawAlienStartingCell(int x, int y, String coord) {
		MapCell label = new MapCell();
		label.setCoord(coord);
		drawCell(x,y,label,"resources/artwork/celle/partenza-alieni.png");
	}
	
	private void drawHumanStartingCell(int x, int y, String coord) {
		MapCell label = new MapCell();
		label.setCoord(coord);
		drawCell(x,y,label,"resources/artwork/celle/partenza-umani.png");
	}
	
	private void drawEscapeCell(int x, int y, String name, String coord) {
		MapCell label = new MapCell(name);
		label.setForeground(Color.WHITE);
		label.setCoord(coord);
		drawCell(x,y,label,"resources/artwork/celle/scialuppa.png");
	}
	
	private void drawDangerousCell(int x, int y, String name) {
		MapCell label = new MapCell(name);
		label.setCoord(name);
		drawCell(x,y,label,"resources/artwork/celle/pericolosa.png");
	}
	
	private void drawSafeCell(int x, int y, String name) {
		MapCell label = new MapCell(name);
		label.setCoord(name);
		drawCell(x,y,label,"resources/artwork/celle/sicura.png");
	}
	
	private void drawCell(int x, int y, MapCell label, String image) {
		label.setPreferredSize(new Dimension(cellWidth, cellHeight));
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setIcon(new ImageIcon(ImageScaler.resizeImage(image, cellWidth, cellHeight)));
		label.addMouseListener(submitMouseAction);
		placeAbsolute(x, y, label);
		setComponentZOrder(label, cell_z);
	}
	
	public void visualizeAndPlace(String position, JLabel label, int correctX, int correctY) {
		try {
			int coord[] = cellToPixels(CoordinatesConverter.fromAlphaNumToOddq(position));
			Insets insets = getInsets();
			Dimension size = label.getPreferredSize();
			label.setBounds(
					coord[0] + insets.left + correctX,
					coord[1] + insets.top + correctY,
		            size.width,
		            size.height);
			label.setVisible(true);
			repaint();
		} catch (BadCoordinatesException e) {
		}
	}
	
	public void visualizeAndPlace(String position, JLabel label) {
		visualizeAndPlace(position, label, 0, 0);
	}
	
	public void highlightCell(String position) {
		visualizeAndPlace(position, highlightOverlay);
	}
	
	public void deHighlight() {
		highlightOverlay.setVisible(false);
	}
	
	/**
	 * Place on the map a nice icon representing the player's position
	 * @param position Alphanum coordinates, the marker will be hidden if set to "unknown"
	 */
	public void setPlayerMarkerPosition(String position) {
		if (position.equals(StringRes.getString("client.applogic.unknownCoordinates"))) {
			playerHereOverlay.setVisible(false);
		} else {
			visualizeAndPlace(position, playerHereOverlay);
		}
	}
	
	/**
	 * add a label by specifying its absolute coordinates
	 * @param x
	 * @param y
	 * @param label
	 */
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
	
	/**
	 * convert a Position2D to an int array
	 * representing the coordinates on the screen
	 * @param posCella
	 * @return
	 */
	private int[] cellToPixels(Position2D posCella) {
		int ans[] = new int[2];
		int size = cellWidth/2;
		int row = posCella.getRow();
		int col = posCella.getCol();
		
		ans[0] = (int) Math.round(size * 3/2 * col);
		ans[1] = (int) Math.round(size * Math.sqrt(3) * (row + 0.5 * (col&1))) - cellHeight;
		
		return ans;
	}
	
	/**
	 * draw a tiled background
	 */
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g); 	//Sometimes it throws the exception: 
	    							//Exception in thread "AWT-EventQueue-0" java.lang.ClassCastException: javax.swing.plaf.FontUIResource cannot be cast to java.awt.Color
	    if (background != null) {
	    	for (int x = 0; x < totalWidth; x += backgroundTileSize) {
	            for (int y = 0; y < totalHeight; y += backgroundTileSize) {
	            	g.drawImage(background, x, y, backgroundTileSize, backgroundTileSize,this);
	            }
	        }
	    }
	    	
	 }
	
	public void addCellListener(MouseListener l) {
		cellListeners.add(l);
	}
	
	public void removeCellListener(MouseListener l) {
		cellListeners.remove(l);
	}
	
	public void addNoiseMarker(String location) {
		noiseManager.addNoise(location, this);
	}
	
	public void clearNoiseMarkers() {
		noiseManager.clearNoises(this);
	}
	
	public void addOtherPlayerMarker(String location, String name) {
		strangerManager.addPlayer(location, name, this);
	}
	
	public void clearOtherPlayerMarkers() {
		strangerManager.clearPlayers(this);
	}
	
	public void removeSpecificPlayer(String name) {
		strangerManager.removeSpecificPlayer(name, this);
	}
	
	public int getTotalWidth() {
		return totalWidth;
	}

	public int getTotalHeight() {
		return totalHeight;
	}
	
	public int getCellWidth() {
		return cellWidth;
	}

	public int getCellHeight() {
		return cellHeight;
	}

	public int getNoiseZ() {
		return noise_z;
	}
}
