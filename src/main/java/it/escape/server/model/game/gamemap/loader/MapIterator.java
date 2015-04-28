package it.escape.server.model.game.gamemap.loader;
import it.escape.server.model.game.gamemap.Cella;

import java.util.Iterator;
import java.util.List;

/**
 * iterator over the "Cella" classes stored in the MapLoader class
 * @author michele
 *
 */
public class MapIterator implements Iterator<Cella> {
	
	private final List<Cella> listaCelle;
	
	private int seek;

	public boolean hasNext() {
		if (!listaCelle.isEmpty() && seek < listaCelle.size()-1) return true;
		return false;
	}

	public Cella next() {
		seek++;
		return listaCelle.get(seek);
	}
	
	public MapIterator(List<Cella> listaCelle) {
		this.listaCelle = listaCelle;
		seek = -1;
		
	}
	
	
	
}
