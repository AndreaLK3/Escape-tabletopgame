package it.escape.core.client.view.gui;

import static org.junit.Assert.*;
import it.escape.tools.utils.swing.ImageScaler;

import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import org.junit.Before;
import org.junit.Test;

public class ObjectCardsPanelTest {
	
	ObjectCardsPanel panel;
	
	@Before
	public void initialize() {
		panel = new ObjectCardsPanel();
		panel.updateCards(Arrays.asList("attack","defense","lights"));
	}
	
	/**Test: the chosenCardName string variable must be "noCard" as default value. 
	 * Clicking a button must update the chosenCardName.*/
	@Test
	public void testClickCardButton() {
		
		assertEquals("noCard", panel.getChosenCardName());
		
		JToggleButton lightsButton = panel.getButtonsAsArray()[2];
		lightsButton.doClick();
		assertEquals("lights", panel.getChosenCardName());
	}

	
	/**Test: giving the panel a list of card names, does it create the expected array of JToggleButtons? 
	 * Every button that is created is compared with the expected one.*/
	@Test
	public void testUpdateCards() {
		JToggleButton expectedArray[] = new JToggleButton[3];
		expectedArray[0] = createCardButton("attack");
		expectedArray[1] = createCardButton("defense");
		expectedArray[2] = createCardButton("lights");
		
		for (int i = 0; i < 3; i++) {
			assertEquals(expectedArray[i].getText(), panel.getButtonsAsArray()[i].getText());
		}
	}
	
	
	/**Private metod used to create a JToggleButton with the text and the image of a card.*/
	private JToggleButton createCardButton(String cardName) {
		JToggleButton cardButton  = new JToggleButton();
		cardButton.setText(cardName);
		
		String cardNames[] = {"attack", "defense","teleport","lights","sedatives","adrenaline"};
		for (int i=0; i<6; i++) {
			if (cardName.equalsIgnoreCase(cardNames[i])) {
				cardButton.setIcon(new ImageIcon(ImageScaler.resizeImage("resources/artwork/ObjectCards/"+cardNames[i]+".png", 50, 46)));
			}
		}
		return cardButton;
	}

}
