package it.escape.client.Graphics;

import it.escape.utils.FilesHelper;

import java.awt.Image;
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
		cardButton.setIcon(new ImageIcon(getImage(cardName)));
		objectCardsButtons.add(cardButton);
		
	}
	
	private Image getImage(String cardName) {
		if (cardName.startsWith("attack")) {
			return ImageScaler.resizeImage("resources/artwork/ObjectCards/Attack.png", 60, 60);
		}
		if (cardName.startsWith("defense")) {
			return ImageScaler.resizeImage("resources/artwork/ObjectCards/Attack.png", 60, 60);		
		}
		if (cardName.startsWith("lights")) {
			return ImageScaler.resizeImage("resources/artwork/ObjectCards/Attack.png", 60, 60);
		}
		if (cardName.startsWith("teleport")) {
			return ImageScaler.resizeImage("resources/artwork/ObjectCards/Attack.png", 60, 60);
		}
		if (cardName.startsWith("sedatives")) {
			return ImageScaler.resizeImage("resources/artwork/ObjectCards/Attack.png", 60, 60);
		}
		if (cardName.startsWith("adrenaline")) {
			return ImageScaler.resizeImage("resources/artwork/ObjectCards/Attack.png", 60, 60);
		}
		
		return null;
	}
	
	
	
	
}




