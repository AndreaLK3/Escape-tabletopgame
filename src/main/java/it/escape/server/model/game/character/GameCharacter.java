package it.escape.server.model.game.character;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.model.game.gamemap.StartingCell;

/**This class defines the behaviour for a generic Character; 
 * it encompasses the functions that are used by both Aliens and Humans.
 * The constructors are in the subclasses.
 * @author andrea
 */
public class GameCharacter {
	
	protected Cell myCell;
	protected int maxDistance;
	protected PlayerTeams team;
	
	
	/**
	 * constructor implementing the operations which are common among all characters
	 * @param start
	 * @param decksRef
	 */
	public GameCharacter(StartingCell start) {
		this.myCell = start;
	}
	
	/**Checks if the intended destination does not exceeds maximum range, 
	 * and if it is walkable by the Character
	 * @param proposedCell
	 * @return true, if movement is in range; otherwise, false 
	 * @throws Exception 
	 */
	public boolean canMove(Cell proposedCell) throws Exception {
		if ((myCell.getPosition().distanceFrom(proposedCell.getPosition()) > maxDistance))
			throw new Exception();
		if (!proposedCell.isWalkable(this))
				throw new Exception();
		
				return true;
	}
	
	/**Performs a (possibly multi-step) movement to a destination
	 * returns true on success
	 * @param proposedCell
	 */
	public void move(Cell proposedCell){
			myCell = proposedCell;
			return;

	}
	
		
	
	public void attack(){}
	
	public Cell getMyCell() {
		return myCell;
	}

	public int getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(int maxDistance) {
		this.maxDistance = maxDistance;
	}
	
	public PlayerTeams getTeam() {
		return team;
	}


}
