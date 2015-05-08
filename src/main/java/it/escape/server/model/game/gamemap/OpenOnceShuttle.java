package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.cards.Card;

public class OpenOnceShuttle extends ShuttleState {

	private boolean alreadyUsed = false;
	
	/**When this function is executed the first time, it returns true.
	 * The subsequent times, it will return false.
	 */
	@Override
	public boolean tryHatch() {
		
		if (alreadyUsed == false)
		{	System.out.println("You managed to find an active escape shuttle!");
			alreadyUsed = true;
			return true;}
		else
		{	System.out.println("Someone has already used this escape shuttle! You have to find another one!");
			return false;
		}
	}

	@Override
	ShuttleState decideState(Card aCard) {
		
		return this;
	}

}
