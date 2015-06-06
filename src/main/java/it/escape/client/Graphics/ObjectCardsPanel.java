package it.escape.client.Graphics;

import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ObjectCardsPanel extends JPanel {

	private JTextField title;
	private List<JRadioButton> objectCardsButtons;
	private ButtonGroup objectButtonsGroup;
	
	public ObjectCardsPanel () {
		
	}
	
	/**
	 * The check on the cardName (that should always be correct anyways, since it is sent by the Server)
	 * has already been done by either the Connection or PlayerState.
	 * @param cardName
	 */
	private void addCard(String cardName) {
		JRadioButton cardButton  = new JRadioButton(cardName);
		cardButton.setIcon(getImage(cardName));
		objectCardsButtons.add(cardButton);
		
	}
	
	private ImageIcon getImage(String cardName) {
		if (cardName.startsWith("attack")) {
			
		}
		if (cardName.startsWith("defense")) {
					
		}
		if (cardName.startsWith("lights")) {
			
		}
		if (cardName.startsWith("teleport")) {
			
		}
		if (cardName.startsWith("sedatives")) {
			
		}
		if (cardName.startsWith("adrenaline")) {
			
		}
		
		return null;
	}
	
	
	
	
}




