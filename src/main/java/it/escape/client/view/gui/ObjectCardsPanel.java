package it.escape.client.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**This class contains a Panel with a sequence of JRadioButtons corresponding to the ObjectCards.
 * This panel is shown in a custom dialog in DumbSwingView, when the showObjectCards button 
 * is pressed (either by the user or as a consequence of a Server message).
 * @author andrea*/
public class ObjectCardsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

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
		objectCardsButtons.clear();
		for (String cardName : cards) {
			addCardButton(cardName);
		}
		chosenCardName = null;
	
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
	 * @return Image (es.attack.png)
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
	
	/**Returns an array of JRadioButtons, that correspond to the cards currently owned.
	 * The array is used inside a JDialog in the View */
	public JRadioButton[] getButtonsAsArray() {
		int x = 0;
		JRadioButton buttonsArray[] = new JRadioButton[NUMCARDTYPES];
		for (JRadioButton b : objectCardsButtons) {
			buttonsArray[x]=b;
			x++;
		}
		return buttonsArray;
	}
	
	/**Returns an array of JRadioButtons, that correspond to the Playable cards currently owned.
	 * The array is used inside a JDialog in the View */
	public JRadioButton[] getPlayableButtonsAsArray() {
		int x = 0;
		JRadioButton buttonsArray[] = new JRadioButton[NUMCARDTYPES];
		for (JRadioButton b : objectCardsButtons) {
			if (!(b.getText().equalsIgnoreCase("attack")) || !(b.getText().equalsIgnoreCase("defense"))) {
				buttonsArray[x]=b;
			}
			x++;
		}
		return buttonsArray;
	}
	
	public String getChosenCardName() {
		return chosenCardName;
	}
	
	public ButtonGroup getGroup() {
		return group;
	}
	
	
	/** This class listens for changes performed on the JRadioButtons 
	 * (i.e. if they are selected), and it assigns the button's text
	 * to the String chosenCardName.
	 * @author andrea */
	private class RadioListener implements ItemListener {

		public void itemStateChanged(ItemEvent event) {
			if (event.getSource() instanceof JRadioButton ) { //toadd: change state on selection
				JRadioButton button = (JRadioButton) event.getSource();
				if (button.isSelected())
				{	chosenCardName = button.getText();
					group.clearSelection();
				}
			}
		}
	}
	

}




