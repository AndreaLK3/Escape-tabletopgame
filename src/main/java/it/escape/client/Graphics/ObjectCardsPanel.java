package it.escape.client.Graphics;

import it.escape.utils.FilesHelper;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ObjectCardsPanel extends JPanel {


	private JTextField title;
	private List<JRadioButton> objectCardsButtons;
	private ButtonGroup group;
	private ItemListener radioListener;
	private String chosenCardName;
	
	
	/** The constructor: when it is called, the ObjectCardsPanel is updated, 
	 * acquiring the cards in the User's possession and adding them as buttons.
	 * @param List<String> cards */
	public ObjectCardsPanel (List<String> cards) {
		objectCardsButtons = new ArrayList<JRadioButton>();
		group = new ButtonGroup();
		radioListener = new RadioListener();
		for (String cardName : cards) {
			addCardButton(cardName);
		}
		
	}
	
	
	/**This method creates a JRadioButton with the given cardName and the corresponding image.
	 * (n:The check on the cardName (that should always be correct anyways, since it is sent by the Server)
	 * has already been done by either the Connection or PlayerState).
	 * @param cardName */
	private void addCardButton(String cardName) {
		JRadioButton cardButton  = new JRadioButton();
		cardButton.setText(cardName);
		cardButton.setIcon(new ImageIcon(getImage(cardName)));

		cardButton.addItemListener(radioListener);
		objectCardsButtons.add(cardButton);
		group.add(cardButton);
		
		
	}
	
	
	/** If the cardName corresponds with one of the known ones, 
	 * this method returns the corresponding image (usually a .png).
	 * @param String cardName
	 * @return Image (es.Attack.png)
	 */
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
	
	
	public void getButtonsAsArray() {
		int x = 0;
		JRadioButton buttonsArray[] = new JRadioButton[4];
		for (JRadioButton b : objectCardsButtons) {
			buttonsArray[x]=b;
			x++;
		}
	}
	
	public String getChosenCardName() {
		return chosenCardName;
	}

	public void getButtonsAsStringArray() {
		int x = 0;
		String buttonsArray[] = new String[4];
		for (JRadioButton b : objectCardsButtons) {
			buttonsArray[x]=b.getActionCommand();
			x++;
		}
	}
	
	
	/** This class listens for changes performed on the JRadioButtons 
	 * (i.e. if they are selected), and it assigns the button's text
	 * to the String chosenCardName.
	 * @author andrea */
	private class RadioListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent event) {
			chosenCardName = ((AbstractButton) event.getSource()).getText();
		}
	}
	
	
	
}




