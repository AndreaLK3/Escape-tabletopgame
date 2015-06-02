package it.escape.client.Graphics;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class EventsFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static EventsFrame playerFrame;

	private JTextField  textField_rename;
	private ActionListener textFieldListener;
	
	private JButton showCardsButton;
	private ButtonListener buttonListener;
	
	private String message;
	
	
	public EventsFrame(String string) {
   		super(string);
   		setLayout(new FlowLayout());
   		
   		initializeTextFields();
   		initializeButtons();
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		setSize(800, 600);
		setVisible(true);
   	}

	
	private void initializeButtons() {
		showCardsButton = new JButton("Show your object Cards");
		
		buttonListener = new ButtonListener();
		showCardsButton.addActionListener(buttonListener);
		
		add(showCardsButton);
	}


	private void initializeTextFields() {
		textField_rename = new JTextField("Enter your name here ", 0);
		
		textFieldListener = new TextFieldListener();
		textField_rename.addActionListener(textFieldListener);
		
		add(textField_rename);
		
	}

	
	private class TextFieldListener implements ActionListener {

		String introduceName = "Your new name is: ";
		
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == textField_rename)
				message = introduceName.concat(event.getActionCommand());
			
			JOptionPane.showMessageDialog(null, message);
			
		}
	}
	

	private class ButtonListener implements ActionListener, ItemListener {

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

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static void main (String args[]) {
		Thread thread1 = new Thread() {
			public void run() {
				playerFrame = new EventsFrame("Escape from Aliens in Outer Space - Events");
				}
			};
			thread1.start();
		
		
	}

}
