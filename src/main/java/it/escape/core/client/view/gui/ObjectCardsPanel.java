package it.escape.core.client.view.gui;

import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**This class contains a Panel with a sequence of JToggleButtons corresponding to the ObjectCards.
 * This panel is shown in a custom dialog in DumbSwingView, when the showObjectCards button 
 * is pressed (either by the user or as a consequence of a ServerSocketCore message).
 * @author andrea*/
public class ObjectCardsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final static int NUMCARDTYPES = 6;
	
	private List<JToggleButton> objectCardsButtons;
	private ButtonGroup group;

	private ItemListener radioListener;
	private String chosenCardName;
	
	
	/** The constructor */
	public ObjectCardsPanel () {
		objectCardsButtons = new ArrayList<JToggleButton>();
		group = new ButtonGroup();
		radioListener = new RadioListener();
	}
	
	
	/** This method updates the List of JToggleButtons, 
	 * according to the List<String> of cardNames received
	 * @param List<String> cards */
	public void updateCards(List<String> cards) {
		objectCardsButtons.clear();
		for (String cardName : cards) {
			addCardButton(cardName);
		}
		chosenCardName = "noCard";
	
	}
	
	/**This method creates a JToggleButton with the given cardName and the corresponding image.
	 * (n:The check on the cardName (that should always be correct anyways, since it is sent by the ServerSocketCore)
	 * has already been done by either the Connection or PlayerState).
	 * @param cardName */
	private void addCardButton(String cardName) {
		JToggleButton cardButton  = new JToggleButton();
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
	
	/**Returns an array of JToggleButtons, that correspond to the cards currently owned.
	 * The array is used inside a JDialog in the View */
	public JToggleButton[] getButtonsAsArray() {
		int x = 0;
		JToggleButton buttonsArray[] = new JToggleButton[NUMCARDTYPES];
		for (JToggleButton b : objectCardsButtons) {
			buttonsArray[x]=b;
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
	
	
	/** This class listens for changes performed on the JToggleButtons 
	 * (i.e. if they are selected), and it assigns the button's text
	 * to the String chosenCardName.
	 * @author andrea */
	private class RadioListener implements ItemListener {

		public void itemStateChanged(ItemEvent event) {
			if (event.getSource() instanceof JToggleButton ) { 
				JToggleButton button = (JToggleButton) event.getSource();
				if (button.isSelected())
				{	chosenCardName = button.getText();
					group.clearSelection();
				}
			}
		}
	}
	

}




