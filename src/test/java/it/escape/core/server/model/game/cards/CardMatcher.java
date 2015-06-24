package it.escape.core.server.model.game.cards;

import it.escape.core.server.model.game.cards.Card;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**This class checks if 2 cards are of the same kind,
 * by checking if they have the same classname
 */
public class CardMatcher extends BaseMatcher<Object> {

	public Card firstCard;
	
	public CardMatcher(Card firstCard) {
		this.firstCard = firstCard;
	}

	@Override
	public boolean matches(Object secondCard) {
			if (secondCard.getClass().getSimpleName().equals(firstCard.getClass().getSimpleName())) {
				return true;
			}
			return false;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("The 2 cards are not equal.");
		
	}

	

}
