package it.escape.server.model.game.gamemap.loader;
import it.escape.server.model.game.gamemap.Cell;

import java.util.Iterator;
import java.util.List;

/**
 * iterator over the "Cell" classes stored in the MapLoader class
 * @author michele
 *
 */
public class MapIterator implements Iterator<Cell> {
	
	private final List<Cell> listaCelle;
	
	private int seek;

	public boolean hasNext() {
		if (!listaCelle.isEmpty() && seek < listaCelle.size()-1) return true;
		return false;
	}

	public Cell next() {
		seek++;
		return listaCelle.get(seek);
	}
	
	public MapIterator(List<Cell> listaCelle) {
		this.listaCelle = listaCelle;
		seek = -1;
		
	}

	public void remove() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
