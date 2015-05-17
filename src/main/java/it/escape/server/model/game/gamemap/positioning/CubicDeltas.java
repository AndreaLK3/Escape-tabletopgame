package it.escape.server.model.game.gamemap.positioning;

import java.util.ArrayList;
import java.util.List;

/**
 * static class with the sole purpose of generating the cubic deltas used in the
 * neighbor cells calculation.
 * reference: http://www.redblobgames.com/grids/hexagons/#neighbors
 * @author michele
 *
 */
public class CubicDeltas {
	
	public static List<PositionCubic> getDeltas() {
		List<PositionCubic> ret = new ArrayList<PositionCubic>();
		ret.add(new PositionCubic(1, -1, 0));
		ret.add(new PositionCubic(1, 0, -1));
		ret.add(new PositionCubic(0, 1, -1));
		ret.add(new PositionCubic(-1, 1, 0));
		ret.add(new PositionCubic(-1, 0, +1));
		ret.add(new PositionCubic(0, -1, +1));
		
		return ret;
	}
}
