package it.escape.server.swinglogviewer;

import it.escape.launcher.menucontroller.StartMenuInterface;
import it.escape.utils.FilesHelper;
import it.escape.utils.LogHelper;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Displays the server log inside a nice swing window.
 * The knack here is that the server routine doesn't know
 * she's running in gui-mode, she just keeps writing to
 * LOGGER, and we capture those logs.
 * @author michele
 *
 */
public class Monitor extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static final String dumpLocation = "eftaios.log";
	
	private boolean rmi;
	private boolean sock;

	private GridBagConstraints c;
	
	private JTextArea messages;
	
	private StartMenuInterface startMenu;

	public Monitor(String string, boolean rmi, boolean sock, StartMenuInterface startMenu) {
		super(string);
		this.rmi = rmi;
		this.sock = sock;
		this.startMenu = startMenu;
		setLayout(new GridBagLayout());
   		c = new GridBagConstraints();
   		
   		createMenu();
   		redirectLog();
   		
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		setSize(600, 450);
   		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void createMenu() {
		setTopRow();
		setTextBody();
		setBottomButtons();
	}

	private void setTopRow() {
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.ipady = 0;
		c.weightx = 1;
		c.weighty = 0;
		JLabel title = new JLabel("EFTAIOS server");
		new Thread(new LiveTitle(sock, rmi, title)).start();
		add(title,c);
	}
	
	private void setTextBody() {
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.ipady = 0;
		c.weightx = 1;
		c.weighty = 1;
		messages = new JTextArea();
		messages.setEditable(false);
		JScrollPane scroller = new JScrollPane(messages);
		
		add(scroller,c);
	}
	
	private void setBottomButtons() {
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.ipady = 0;
		c.weightx = 1;
		c.weighty = 0;
		JButton dump = new JButton("Dump to file: \"" + dumpLocation + "\"");
		dump.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					FilesHelper.saveToFile(dumpLocation, messages.getText());
				} catch (FileNotFoundException e1) {
				}
			}
		});
		add(dump,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.ipady = 0;
		c.weightx = 1;
		c.weighty = 0;
		JButton quit = new JButton("Stop server");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startMenu.closeProgram();
			}
		});
		add(quit,c);
	}
	
	private void redirectLog() {
		LogHelper.setDefaultHandler(
				new ResponsiveStreamHandler(
						new VisualizeMessageStream(messages),
						LogHelper.getDefaultFormatter()));
	}
	
	public static void synchronousLaunch(final boolean rmi, final boolean sock, final StartMenuInterface startMenu) {
		SwingSynchroLauncher.synchronousLaunch(new Runnable() {
			public void run() {
				new Monitor("Server log",rmi,sock, startMenu);
			}});
	}
}
