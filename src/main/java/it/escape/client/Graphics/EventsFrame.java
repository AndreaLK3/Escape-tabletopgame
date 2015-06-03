package it.escape.client.Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import it.escape.client.model.PlayerState;


public class EventsFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private PlayerState playerState;
	
	private JTextField  textField_rename;
	private ActionListener textFieldHandler;
	
	private JPanel objectsPanel;
	
	private JButton showCardsButton;
	
	private List<JRadioButton> objectCardsButtons;
	private ButtonGroup objectButtonsGroup;
	
	private EventListener eventHandler;
	private RadioButtonsHandler radioButtonsHandler;
	
	private String message;
	
	
	
	public EventsFrame(String string, PlayerState player) {
   		super(string);
   		setLayout(new BorderLayout());
   		this.playerState = player;
   		objectsPanel = new JPanel();
   		objectsPanel.setLayout(new FlowLayout());
   		add(objectsPanel, BorderLayout.SOUTH);
   		eventHandler = new EventListener();
   		textFieldHandler = new TextFieldListener();
   		
   		initializeTextFields();
   		
   		initializeButtons();
   		
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		setSize(800, 600);
		setVisible(true);
   	}

	
	private void initializeButtons() {
	
		showCardsButton = new JButton("Show your object Cards");
		showCardsButton.addActionListener(eventHandler);
		add(showCardsButton,BorderLayout.NORTH);
		
		objectCardsButtons = new ArrayList<JRadioButton>(); 
		
		objectButtonsGroup = new ButtonGroup();
		
		for (String s : playerState.getObjectCards()) {
			JRadioButton objectCardButton = createObjectButton(s, objectButtonsGroup, radioButtonsHandler);
			objectCardsButtons.add(objectCardButton);
		}
		
		for (JRadioButton b : objectCardsButtons)
			addButtonToContainer(objectsPanel, b);
		
		
	}

	/**This class creates a JRadioButton, adds it to the specified group of buttons,
	 * and defines the observer object for the button*/
	private JRadioButton createObjectButton(String s,ButtonGroup group,RadioButtonsHandler handler) {
		JRadioButton newButton = new JRadioButton(s);
		group.add(newButton);
		newButton.addItemListener(handler);
		return newButton;
	}

	private void addButtonToContainer(Container container, AbstractButton button) {
		container.add(button);
	}

	private void initializeTextFields() {
		textField_rename = new JTextField("Enter your name here ", 0);
		
		textField_rename.addActionListener(textFieldHandler);
		
		add(textField_rename);
		
	}

	/**This class listens for the ActionEvents that may be produced
	 * by one of the text fields, and handles them.
	 * @author andrea */
	private class TextFieldListener implements ActionListener {

		String introduceName = "Your new name is: ";
		
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == textField_rename)
				message = introduceName.concat(event.getActionCommand());
			
			JOptionPane.showMessageDialog(null, message);
			
		}
	}
	

	/**This class listens for the ActionEvents that may be produced
	 * by JButtons, and handles them.*/
	private class EventListener implements ActionListener{

		String introduceName = "Your new name is: ";
		String cardsWillGoHere = "Here there will be a list of the currently owned cards,"
								+ "first with strings and then with images, too";
		
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == textField_rename) {
				message = introduceName.concat(event.getActionCommand());
			}
			if (event.getSource() == showCardsButton) {
				message = cardsWillGoHere;
			}
	
			JOptionPane.showMessageDialog(null, message);
		}
	}  
	
	private class RadioButtonsHandler implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static void main (String args[]) {
		Thread thread1 = new Thread() {
			public void run() {
				new EventsFrame("Escape from Aliens in Outer Space - Events", new PlayerState());
				}
			};
			thread1.start();
		
		
	}

}
