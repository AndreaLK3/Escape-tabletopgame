package it.escape.launcher;

import it.escape.CliParser;
import it.escape.client.Graphics.ImageAutoFit;
import it.escape.launcher.menucontroller.ActionQuit;
import it.escape.launcher.menucontroller.ActionSetNetMode;
import it.escape.launcher.menucontroller.ActionSetUserExperience;
import it.escape.launcher.menucontroller.ActionStartClient;
import it.escape.launcher.menucontroller.ActionStartServer;
import it.escape.launcher.menucontroller.StartMenuInterface;
import it.escape.strings.StringRes;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class StartMenu extends JFrame implements StartMenuInterface {

	private static final long serialVersionUID = 1L;
	
	private GridBagConstraints c;
	
	private String[] buttons = {
			StringRes.getString("launcher.button.client"),
			StringRes.getString("launcher.button.server"),
			StringRes.getString("launcher.button.quit")};
	
	private String[] netmodes = {
			StringRes.getString("launcher.option.netmode.socket"),
			StringRes.getString("launcher.option.netmode.RMI")};
	
	private String[] experience = {
			StringRes.getString("launcher.option.experience.graphical"),
			StringRes.getString("launcher.option.experience.textual")};
	
	private int growMenu;
	
	private LauncherState state;
	
	public StartMenu(String string) {
   		super(string);
   		state = new LauncherState();
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
   		JButton client = setButton(0);
   			client.addActionListener(new ActionStartClient(state, this));
   		JButton server = setButton(1);
   			server.addActionListener(new ActionStartServer(state, this));
   		JComboBox<String> net = setCBox(netmodes);
   			net.addActionListener(new ActionSetNetMode(state));
   		JComboBox<String> ui = setCBox(experience);
   			ui.addActionListener(new ActionSetUserExperience(state));
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
	
	public void tbiMessage() {
		JOptionPane.showMessageDialog(null,
		    "This feature is currently *not* implemented",
		    "Oops!",
		    JOptionPane.WARNING_MESSAGE);
	}
	
	public void closeMenu() {
		this.setVisible(false);
	}
	
	public void closeProgram() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public static void launch() throws HeadlessException {
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
		if (ge.isHeadless()) {
			throw new HeadlessException();
		}
		
		EventQueue.invokeLater(
			new Runnable() {
				public void run() {
					StartMenu menu = new StartMenu("Escape from the Aliens in Outer Space");
				}
			});
	}
}
