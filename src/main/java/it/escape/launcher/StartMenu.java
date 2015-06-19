package it.escape.launcher;

import it.escape.client.view.gui.ImageAutoFit;
import it.escape.client.view.gui.ImageScaler;
import it.escape.launcher.menucontroller.ActionAcceptNewPortNumber;
import it.escape.launcher.menucontroller.ActionQuit;
import it.escape.launcher.menucontroller.ActionSetOption;
import it.escape.launcher.menucontroller.ActionStartClient;
import it.escape.launcher.menucontroller.ActionStartServer;
import it.escape.launcher.menucontroller.LauncherLocalSettings;
import it.escape.launcher.menucontroller.StartMenuInterface;
import it.escape.launcher.menucontroller.StartSubsystemsInterface;
import it.escape.server.view.rmispecific.ServerRMI;
import it.escape.strings.StringRes;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
			StringRes.getString("launcher.button.wizard"),
			StringRes.getString("launcher.button.quit")};
	
	private String[] icons = {
			"resources/artwork/launcher/icon-play.png",
			"resources/artwork/launcher/icon-server.png",
			"resources/artwork/launcher/icon-setup.png",
			"resources/artwork/launcher/icon-quit.png"};
	
	private String[] netmodesServer = {
			StringRes.getString("launcher.option.netmode.socket"),
			StringRes.getString("launcher.option.netmode.RMI"),
			StringRes.getString("launcher.option.netmode.combo")};
	
	private String[] netmodesClient = {
			StringRes.getString("launcher.option.netmode.socket"),
			StringRes.getString("launcher.option.netmode.RMI")};
	
	private String[] experience = {
			StringRes.getString("launcher.option.experience.graphical"),
			StringRes.getString("launcher.option.experience.textual")};
	
	private int growMenu;
	
	private LauncherState stateForClient;
	
	private LauncherState stateForServer;
	
	public StartMenu(String string, LauncherLocalSettings locals, StartSubsystemsInterface starter) {
   		super(string);
   		this.locals = locals;
   		this.starter = starter;
   		stateForClient = new LauncherState();
   		stateForServer = new LauncherState();
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
   			client.addActionListener(new ActionStartClient(stateForClient, this));
   		JButton server = setButton(1);
   			server.addActionListener(new ActionStartServer(stateForServer, this));
   			
   		JLabel labelCliOpt = setLabelBeforeField("Client settings:");
   			labelCliOpt.setToolTipText("Client-only settings");
   		JComboBox<String> cliNet = setCBox(netmodesClient, 1);
   			cliNet.addActionListener(new ActionSetOption(stateForClient, true));
   		JComboBox<String> cliUI = setCBox(experience, 2);
   			cliUI.addActionListener(new ActionSetOption(stateForClient, false));
   			
   		JLabel labelSrvOpt = setLabelBeforeField("Server settings:");
   			labelSrvOpt.setToolTipText("Server-only settings");
   		JComboBox<String> srvNet = setCBox(netmodesServer, 1);
   			srvNet.addActionListener(new ActionSetOption(stateForServer, true));
   		JComboBox<String> srvUI = setCBox(experience, 2);
   			srvUI.addActionListener(new ActionSetOption(stateForServer, false));
   			
   		JLabel portOption = setLabelBeforeField("Port:");
   			portOption.setToolTipText("Port to listen on / to connect to. "
   					+ "note: the regisrty (RMI only) will listen on the default port " + ServerRMI.REGISRTY_PORT
   					+ "; if the combo Socket + RMI mode is selected, rmi will take over"
   					+ " this port, and Socket will get the next avaible port");
   		JTextField portno = setTextField("" + locals.getServerPort());
   		JButton acceptPort = setAcceptButton("Set port");
   			acceptPort.addActionListener(new ActionAcceptNewPortNumber(portno, locals));
   		JButton quit = setButton(3);
   			quit.addActionListener(new ActionQuit(this));
	}

	private JLabel setTopImage() {
		JLabel top = new JLabel();
		top.setOpaque(true);
		top.setBackground(new Color(0, 0, 0));
		
		new ImageAutoFit(top, "resources/artwork/launcher/splash-logo.jpg").setUpAutoFittingImage();
		
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
	
	private JComboBox<String> setCBox(String[] items, int pos) {
		JComboBox<String> box = new JComboBox<String>(items);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = pos;
		c.gridy = growMenu-pos;
		c.gridwidth = 1;
		c.ipady = 0;
		c.weightx = 1;
		c.weighty = 0;
		
		add(box,c);
		growMenu += 1;
		return box;
	}
	
	private JLabel setLabelBeforeField(String value) {
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
	
	/**
	 * Throws HeadlessException if the GUI in unavailable,
	 * otherwise, id doesn't do anything
	 * @throws HeadlessException
	 */
	public static void detectTextOnlyEnvironment() throws HeadlessException {
		if (GraphicsEnvironment.isHeadless()) {
			throw new HeadlessException();
		}
	}
	
	public static void launch(final LauncherLocalSettings locals, final StartSubsystemsInterface starter) throws HeadlessException {

		detectTextOnlyEnvironment();
		
		EventQueue.invokeLater(
			new Runnable() {
				public void run() {
					StartMenu menu = new StartMenu("Escape from the Aliens in Outer Space", locals, starter);
				}
			});
	}
}
