package it.escape.launcher;

import it.escape.client.Graphics.ImageAutoFit;
import it.escape.launcher.menucontroller.ActionQuit;
import it.escape.strings.StringRes;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StartMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	GridBagConstraints c;
	
	private String[] buttons = {
			"Start client",
			"Start server",
			StringRes.getString("launcher.button.quit")};
	
	private String[] netmodes = {
			StringRes.getString("launche.option.netmode.socket"),
			"RMI"};
	
	private String[] experience = {"Graphical","Textual"};
	
	private int growMenu;
	
	public StartMenu(String string) {
   		super(string);
   		setLayout(new GridBagLayout());
   		c = new GridBagConstraints();
   		
   		growMenu = 0;
   		
   		createMenu();
   		
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		setSize(400, 400);
		setVisible(true);
   	}
	
	private void createMenu() {
		setTopImage();
   		setButton(0);
   		setButton(1);
   		setCBox(netmodes);
   		setCBox(experience);
   		JButton quit = setButton(2);
   		quit.addActionListener(new ActionQuit());
	}

	private JLabel setTopImage() {
		JLabel top = new JLabel();
		top.setOpaque(true);
		top.setBackground(new Color(0, 0, 0));
		
		new ImageAutoFit(top, "resources/artwork/splash-logo.jpg");
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 0;
		c.weightx = 1;
		c.weighty = 1;	
		c.anchor = GridBagConstraints.CENTER;
		add(top,c);
		growMenu += 4;
		return top;
	}
	
	private JButton setButton(int num) {
		String name;
		
		try {
			name = buttons[num];
		} catch (ArrayIndexOutOfBoundsException e) {
			name = "default";
		}
		
		JButton bt = new JButton(name);
		bt.setActionCommand(name);
		//bt.setBackground(new Color(255, 0, 0));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = growMenu;
		c.ipady = 0;
		c.weightx = 1;
		c.weighty = 0;
		//c.anchor = GridBagConstraints.PAGE_START;
		add(bt,c);
		growMenu += 1;
		return bt;
	}
	
	private JComboBox<String> setCBox(String[] items) {
		JComboBox<String> box = new JComboBox<String>(items);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = growMenu;
		c.ipady = 0;
		c.weightx = 1;
		c.weighty = 0;
		
		add(box,c);
		growMenu += 1;
		return box;
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
