package it.escape.client.Graphics;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StartMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	GridBagConstraints c;
	
	private String[] buttons = {"Client","Client RMI","Server","Server RMI"};

	public StartMenu(String string) {
   		super(string);
   		setLayout(new GridBagLayout());
   		c = new GridBagConstraints();
   		
   		setTopImage();
   		setButton(0);
   		setButton(1);
   		setButton(2);
   		setButton(3);
   		
   		
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		setSize(400, 400);
		setVisible(true);
   	}

	private void setTopImage() {
		JLabel top = new JLabel();
		//top.setBorder(BorderFactory.createLineBorder(Color.black));
		top.setBackground(new Color(255, 0, 0));
		
		new ImageAutoFit(top, "resources/galilei.jpg");
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 0;
		c.weightx = 1;
		c.weighty = 1;	
		//c.anchor = GridBagConstraints.FIRST_LINE_START;
		add(top,c);

	}
	
	private void setButton(int num) {
		JButton bt = new JButton(buttons[num]);
		//bt.setBackground(new Color(255, 0, 0));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4 + num;
		c.ipady = 0;
		c.weightx = 1;
		c.weighty = 0;
		//c.anchor = GridBagConstraints.PAGE_START;
		add(bt,c);
	}
	
	public static void main( String[] args )
    {
		EventQueue.invokeLater(
			new Runnable() {
				public void run() {
					StartMenu menu = new StartMenu("Escape from the Aliens in Outer Space");
			
				}
			});
	}
}
