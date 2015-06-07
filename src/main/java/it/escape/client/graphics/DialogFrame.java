package it.escape.client.graphics;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DialogFrame extends JFrame {
	
	private static DialogFrame playerFrame;
	
	
	
	public DialogFrame(String string) {
		super(string);
		setLayout(new FlowLayout());
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		setSize(800, 600);
		setVisible(true);
	}

	
  	private void turnLoop() {
  		String title1="Movement";
  		String s1="Insert a position";
  		String title2="ObjectCard";
  		String s2="Do you wish to play an ObjectCard?";
		
  		JOptionPane.showInputDialog(null, s1, title1, JOptionPane.PLAIN_MESSAGE );	//if the user clicks OK, the dialog returns the String typed by the user
  																					//if the user clicks Cancel, the dialog returns null
  		JOptionPane.showConfirmDialog(null, s2, title2, JOptionPane.YES_NO_OPTION);
  		
	}
	
	
	
	public static void main( String[] args )
    {
		Thread thread1 = new Thread() {
							public void run() {
								playerFrame = new DialogFrame("Escape from the Aliens in Outer Space - Dialogs");
								playerFrame.turnLoop();
								}
							};
		thread1.start();
		
	}


	


	

}
