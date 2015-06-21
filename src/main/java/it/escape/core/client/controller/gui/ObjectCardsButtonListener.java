package it.escape.core.client.controller.gui;

import it.escape.core.client.controller.Relay;
import it.escape.core.client.view.gui.ObjectCardsPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**This class listens to the JButton ShowCardsButton.
 * 1) When the user clicks it, it shows a dialog with objectCardsPanel,
 * which contains the object Cards currently owned.
 * 2) When the Updater "clicks" it, because the ServerSocketCore requires an Object Card, 
 * it shows a dialog with the playable objectCards, and,
 * depending on the user's choice, a String with the CardName is obtained and sent to the Relay.
 * @author andrea*/
public class ObjectCardsButtonListener implements ActionListener{
	
	private ObjectCardsPanel objectCardsPanel;
	
	private Relay relayRef;
	
	private boolean doRelayObjectCard;
	
	private String chosenObjectCard;
	
	/**The constructor*/
	public ObjectCardsButtonListener(ObjectCardsPanel objectCardsPanel, Relay relayRef) {
	
		this.objectCardsPanel = objectCardsPanel;
		this.relayRef = relayRef;
		doRelayObjectCard = false;
		chosenObjectCard = null;
		
	}
	
	/**The function that is actually invoked when the user or the Server presses the button*/
	public void actionPerformed(ActionEvent event) {
				new Thread(
					new Runnable() {
						public void run() {
							if (doRelayObjectCard) {
								chosenObjectCard = null;
								do {
								JOptionPane.showConfirmDialog(null, objectCardsPanel.getPlayableButtonsAsArray(), 
										"Your object cards", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
								chosenObjectCard = objectCardsPanel.getChosenCardName();
								if (chosenObjectCard == null){
									JOptionPane.showMessageDialog(null, "You haven't chosen any card.");
								}
								else {
									JOptionPane.showMessageDialog(null, "You have chosen the " + chosenObjectCard  + " card.");
								}
								}while(chosenObjectCard == null);	//note: the user must choose a valid object card name
																			//(even an unplayable one).
								relayRef.relayMessage(chosenObjectCard);	//This line sends the name of the chosen objectCard to the ServerSocketCore
								doRelayObjectCard = false;					//This line resets the variable.
							}
							else {
								JOptionPane.showConfirmDialog(null, objectCardsPanel.getButtonsAsArray(), 
											"Your object cards", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
							}
							}
						}).start();
			}

	public boolean getDoRelayObjectCard() {
		return doRelayObjectCard;
	}

	public void setDoRelayObjectCard(boolean doRelayObjectCard) {
		this.doRelayObjectCard = doRelayObjectCard;
	}
	}
