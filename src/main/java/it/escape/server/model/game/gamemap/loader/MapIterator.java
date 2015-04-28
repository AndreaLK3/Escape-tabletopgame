package it.escape.server.model.game.gamemap.loader;
import it.escape.server.model.game.gamemap.Cella;

import java.util.Iterator;
import java.util.List;

/**
 * <IN COSTRUZIONE>
 * Oggetto con lo scopo di caricare in memoria un file "mappa", l'oggetto funge da:
 * 1) iteratore sulle celle lette da file
 * 2) restituisce il nome e il range della mappa
 * La codifica è ancora da stabilire, consiglio JSON
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
