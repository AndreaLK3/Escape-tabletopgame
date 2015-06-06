package it.escape.launcher;

import it.escape.client.Graphics.ImageAutoFit;
import it.escape.client.Graphics.ImageScaler;
import it.escape.launcher.menucontroller.ActionAcceptNewPortNumber;
import it.escape.launcher.menucontroller.ActionQuit;
import it.escape.launcher.menucontroller.ActionSetNetMode;
import it.escape.launcher.menucontroller.ActionSetUserExperience;
import it.escape.launcher.menucontroller.ActionStartClient;
import it.escape.launcher.menucontroller.ActionStartServer;
import it.escape.launcher.menucontroller.LauncherLocalSettings;
import it.escape.launcher.menucontroller.StartMenuInterface;
import it.escape.launcher.menucontroller.StartSubsystemsInterface;
import it.escape.strings.StringRes;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * The view of our mini MVC, initialize and show the graphic interface, as well as
 * LauncherState (the model) and the various ActionListeners (the controller)
 * @author michele
 *
 */
public class StartMenu extends JFrame implements StartMenuInterface {

	private static final long serialVersionUID = 1L;
	
	private LauncherLocalSettings locals;
	
	private StartSubsystemsInterface starter;
	
	private static final int ICON_SIZE = 24;
	
	private GridBagConstraints c;
	
	private String[] buttons = {
			StringRes.getString("launcher.button.client"),
			StringRes.getString("launcher.button.server"),
			StringRes.getString("launcher.button.quit")};
	
	private String[] icons = {
			"resources/artwork/launcher/icon-play.png",
			"resources/artwork/launcher/icon-server.png",
			"resources/artwork/launcher/icon-quit.png"};
	
	private String[] netmodes = {
			StringRes.getString("launcher.option.netmode.socket"),
			StringRes.getString("launcher.option.netmode.RMI")};
	
	private String[] experience = {
			StringRes.getString("launcher.option.experience.graphical"),
			StringRes.getString("launcher.option.experience.textual")};
	
	private int growMenu;
	
	private LauncherState state;
	
	public StartMenu(String string, LauncherLocalSettings locals, StartSubsystemsInterface starter) {
   		super(string);
   		this.locals = locals;
   		this.starter = starter;
   		state = new LauncherState();
   		setLayout(new GridBagLayout());
   		c = new GridBagConstraints();
   		
   		growMenu = 0;
   		
   		createMenu();
   		
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		setSize(400, 450);
   		setLocationRelativeTo(null);
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
   		setLabelBeforeTextField("Port:");
   		JTextField portno = setTextField("" + locals.getServerPort());
   		JButton acceptPort = setAcceptButton("Change port number");
   			acceptPort.addActionListener(new ActionAcceptNewPortNumber(portno, locals));
   		JButton quit = setButton(2);
   			quit.addActionListener(new ActionQuit(this));
	}

	private JLabel setTopImage() {
		JLabel top = new JLabel();
		top.setOpaque(true);
		top.setBackground(new Color(0, 0, 0));
		
		new ImageAutoFit(top, "resources/artwork/launcher/splash-logo.jpg");
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
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
		Icon icon = null;
		
		try {
			name = buttons[num];
		} catch (ArrayIndexOutOfBoundsException e) {
			name = "default";
		}

		try {
			icon = new ImageIcon(ImageScaler.resizeImage(icons[num], ICON_SIZE, ICON_SIZE));
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		
		JButton bt = new JButton(name);
		bt.setActionCommand(name);
		if (icon != null) {
			bt.setIcon(icon);
		}
		//bt.setBackground(new Color(255, 0, 0));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = growMenu;
		c.gridwidth = 3;
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
		c.gridwidth = 3;
		c.ipady = 0;
		c.weightx = 1;
		c.weighty = 0;
		
		add(box,c);
		growMenu += 1;
		return box;
	}
	
	private JLabel setLabelBeforeTextField(String value) {
		JLabel lab = new JLabel(value);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = growMenu;
		c.gridwidth = 1;
		c.ipady = 0;
		c.weightx = 0.2;
		c.weighty = 0;
		
		add(lab,c);
		growMenu += 1;
		
		return lab;
	}
	
	private JTextField setTextField(String value) {
		JTextField text = new JTextField(value);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = growMenu-1;
		c.gridwidth = 1;
		c.ipady = 0;
		c.weightx = 1;
		c.weighty = 0;
		
		add(text,c);
		growMenu += 1;
		
		return text;
	}
	
	private JButton setAcceptButton(String label) {
		JButton bt = new JButton(label);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = growMenu-2;
		c.gridwidth = 1;
		c.ipady = 0;
		c.weightx = 1;
		c.weighty = 0;
		
		add(bt,c);
		growMenu += 1;
		
		return bt;
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
	
	public LauncherLocalSettings getLocalSettings() {
		return locals;
	}
	
	public StartSubsystemsInterface getStarter() {
		return starter;
	}
	
	public static void launch(final LauncherLocalSettings locals, final StartSubsystemsInterface starter) throws HeadlessException {

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
		if (ge.isHeadless()) {
			throw new HeadlessException();
		}
		
		EventQueue.invokeLater(
			new Runnable() {
				public void run() {
					StartMenu menu = new StartMenu("Escape from the Aliens in Outer Space", locals, starter);
				}
			});
	}
}
