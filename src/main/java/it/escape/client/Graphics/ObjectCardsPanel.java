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

	private final static int NUMCARDTYPES = 6;
	
	private List<JRadioButton> objectCardsButtons;
	private ButtonGroup group;
	private ItemListener radioListener;
	private String chosenCardName;
	
	
	/** The constructor */
	public ObjectCardsPanel () {
		objectCardsButtons = new ArrayList<JRadioButton>();
		group = new ButtonGroup();
		radioListener = new RadioListener();
	}
	
	
	/** This method updates the List of JRadioButtons, 
	 * according to the List<String> of cardNames received
	 * @param List<String> cards */
	public void updateCards(List<String> cards) {
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
		String cardNames[] = {"attack", "defense","teleport","lights","sedatives","adrenaline"};
		for (int i=0; i<NUMCARDTYPES; i++) {
			if (cardName.equalsIgnoreCase(cardNames[i])) {
				return ImageScaler.resizeImage("resources/artwork/ObjectCards/"+cardNames[i]+".png", 50, 46);
			}
		}
		
		
		
		return null;
	}
	
	
	public JRadioButton[] getButtonsAsArray() {
		int x = 0;
		JRadioButton buttonsArray[] = new JRadioButton[6];
		for (JRadioButton b : objectCardsButtons) {
			buttonsArray[x]=b;
			x++;
		}
		return buttonsArray;
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




