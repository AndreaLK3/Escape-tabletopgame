package it.escape.core.client.view.gui;

import it.escape.core.client.controller.Relay;
import it.escape.core.client.controller.gui.ActionSendChat;
import it.escape.core.client.controller.gui.MouseOnMapCell;
import it.escape.core.client.controller.gui.NameListener;
import it.escape.core.client.model.GameStatus;
import it.escape.core.client.view.gui.maplabel.MapViewer;
import it.escape.core.server.model.game.exceptions.BadJsonFileException;
import it.escape.tools.utils.FilesHelper;
import it.escape.tools.utils.swing.ImageScaler;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


/**This class contains the Frame that is displayed for the Client that uses the GUI.
 * This is a dumb class, responsible only for initialization / layout of the components,
 * The 'active' role is delegated to the subclass SmartSwingView.
 * Responsibilities
 * 1) Containing all the components
 * 2) Creating and initializing the components
 * 
 * Note: this class does actually carry on small 'active' roles, which are
 * Very closely aggregated to it and should not be moved elsewhere:
 * 1) Showing the object-cards display/choose dialog
 * 2) Handling a click on the disconnect button
 * 3) Attaching the relay to said cards-dialog, and to the chat textfield
 * 
 * The class is made abstract, because it's useless on its own and should not be
 * directly instantiated
 * @author andrea, michele
 */
public abstract class DumbSwingView extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger( DumbSwingView.class.getName() );
	
	protected static final int MAXPLAYERS = 8;
	
	protected static final int CONN_ICON_SIZE = 20;
  	
	private static final double left_panels = 0.5;
	private static final double map_panel = 1.0;
	
	protected GridBagConstraints constraints;
   	
	protected JLabel label0ServerStatus;
	protected JLabel label1NumOfPlayers;
	protected JTextField numOfPlayersField;
   	protected JLabel label2GameStatus;
   	protected JTextArea gameStatusArea;
   	protected JLabel label3TurnNumber;
   	protected JTextField turnNumberField;
   	protected JLabel label4;
   	protected JLabel label5;
   	protected JLabel label6;
   	protected JLabel label7Map;
   	protected JLabel label8;
   	protected JLabel label9;
	protected JLabel label10;
	protected JLabel label11TurnStatus;
	protected JLabel label12Chat;
	protected JLabel label13CardNotify;
	protected JScrollPane mapScrollPane;
	
	protected JTextField nameField;
	protected JTextField statusArea;
	protected JTextField teamArea;
	protected JTextField serverField;
	protected JTextField chatField;
	protected JTextArea chatArea;
	protected JButton showCardsButton;
	
	protected JButton buttonDisconnect;
	
	protected ObjectCardsPanel objectCardsPanel;
	
	protected transient Relay relayRef;
	
	protected PlayerPanel playerPanels[] = new PlayerPanel[MAXPLAYERS];
	
	/**Note: this reference is made transient, since JFrame is serializable but the feature is not used. */
	protected transient NameListener myNameListener;
	protected transient ObjectCardsButtonListener buttonHandler;
	
	protected int currentRow = 1;
	
	/**
	 * The constructor: initializes the window and all of its containers and components.
	 * @param string (the title of the window) 
	 * [@param BindUpdaterInterface (updater that will feed us data from the net) 
	 * note: removed, since we don't need this connection (see Client Diagram), and it creates a cycle too]
	 * @param Relay (used to send data to the net)
	 */
   	public DumbSwingView(String string, Relay relayRef) {
   		super(string);
   		this.relayRef = relayRef;
   		myNameListener = new NameListener(relayRef);
   		objectCardsPanel = new ObjectCardsPanel();
   		buttonHandler = new ObjectCardsButtonListener(objectCardsPanel, relayRef);
   		setLayout(new GridBagLayout());
   		constraints = new GridBagConstraints();
   		
   		
   		initializeGeneralLabels();
   		initializeMap();
   		initializeGeneralSidePanels();
   		initializePersonalPanels();
   		addTurnStatusPanel();
   		addChatPanel();
   		addCardNotifyZone();
   		initializeButtons();
   		setLabelsOpaque();
   		

   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		setSize(950, 600);
		setVisible(true);
   	}
   	
   	/** Creation method: Labels in the upper part of the screen*/
	private void initializeGeneralLabels() {
		
		JPanel panel = new JPanel();
		
		buttonDisconnect = new JButton("Disconnect");
		buttonDisconnect.addActionListener(new ActionDisconnectButton());
		
		label0ServerStatus = new JLabel(new ImageIcon(ImageScaler.resizeImage("resources/artwork/misc/check.png", CONN_ICON_SIZE, CONN_ICON_SIZE)));
		label0ServerStatus.setText("Connection: ");
		label0ServerStatus.setToolTipText("Online");
		label0ServerStatus.setHorizontalTextPosition(JLabel.LEFT);
		label0ServerStatus.setVerticalTextPosition(JLabel.CENTER);
		
		label1NumOfPlayers = new JLabel ("PlayersConnected: ");
		label1NumOfPlayers.setHorizontalAlignment(SwingConstants.RIGHT);
		numOfPlayersField = new JTextField("0/8");
		numOfPlayersField.setEditable(false);
		
		label2GameStatus = new JLabel("Game Status:");
		label2GameStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		gameStatusArea = new JTextArea(GameStatus.WAITING_FOR_PLAYERS.toString());
		gameStatusArea.setEditable(false);
		
		label3TurnNumber = new JLabel("Turn Number:");
		label3TurnNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		turnNumberField = new JTextField("0");
		turnNumberField.setEditable(false);
		
		panel = createRowPanel(Arrays.asList(buttonDisconnect, label0ServerStatus, label1NumOfPlayers, numOfPlayersField,
											label2GameStatus, gameStatusArea, label3TurnNumber, turnNumberField));
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
	    add(panel,constraints);
	    resetConstraints(constraints);
	
	    
		label4 = new JLabel("Players");
		label4.setBackground(new Color(150, 130, 230));
		label4.setHorizontalAlignment(SwingConstants.CENTER);
		
		label5 = new JLabel("Status");
		label5.setBackground(new Color(110, 220, 220));
		label5.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		label6 = new JLabel("LastLocation");
		label6.setBackground(new Color(50, 100, 200));
		label6.setHorizontalAlignment(SwingConstants.CENTER);
		panel = createRowPanel(Arrays.asList(label4, label5, label6));
		addSidePanel(panel);
	
	}
	
	/** Creation method: the map, on the middle right part of the screen*/
	protected void initializeMap() {

		try {
			label7Map = new MapViewer();
		} catch (BadJsonFileException e) {
			label7Map = new JLabel("Bad json map file");
			LOGGER.warning("Bad Json map file" + e.getMessage());
		} catch (IOException e) {
			label7Map = new JLabel("Cannot open map file");
			LOGGER.warning("Can not open map file" + e.getMessage());
		}
		((MapViewer)label7Map).addCellListener(new MouseOnMapCell((MapViewer)label7Map));
		mapScrollPane = new JScrollPane(label7Map);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = map_panel;
		constraints.weighty = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 13;
		constraints.anchor = GridBagConstraints.CENTER;
		add(mapScrollPane, constraints);
		resetConstraints(constraints);
		
	}
	
	
	/** Creation method: the PlayerPanels, with a the Players and their status*/
   	private void initializeGeneralSidePanels() {
   	
		for (int x=0; x < MAXPLAYERS ; x++) {
			playerPanels[x] = new PlayerPanel();
			addSidePanel(playerPanels[x]);
		}
			
   	}
   	
	/** Creation method: my name(editable), status and team*/
   	private void initializePersonalPanels() {
	
   		JPanel panel = new JPanel();
   		
		label8 = new JLabel("MyName");
		label8.setHorizontalAlignment(SwingConstants.CENTER);
		label8.setBackground(new Color(150, 200, 240));
		
		label9 = new JLabel("Status");
		label9.setHorizontalAlignment(SwingConstants.CENTER);
		label9.setBackground(new Color(180, 130, 240));
		
		label10 = new JLabel("Team");
		label10.setHorizontalAlignment(SwingConstants.CENTER);
		label10.setBackground(new Color(150, 150, 220));
		panel = createRowPanel(Arrays.asList(label8, label9, label10));
		addSidePanel(panel);
		
		
		nameField = new JTextField("Enter your name in this field");
		nameField.addActionListener(myNameListener);
		
		statusArea = new JTextField("Status");
		statusArea.setEditable(false);
		
		teamArea = new JTextField("Team");
		teamArea.setEditable(false);
		
		panel = createRowPanel(Arrays.asList(nameField, statusArea, teamArea));
		addSidePanel(panel);
		
	}
   	
   	/** Creation method: chat text area and text field*/
   	private void addTurnStatusPanel() {
   		label11TurnStatus = new JLabel("Turn Status");
   		label11TurnStatus.setBackground(new Color(150, 100, 150));
		
   		serverField = new JTextField();
   		serverField.setText("Waiting for the game to start");
   		serverField.setEditable(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.NONE;
		panel.add(label11TurnStatus, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		panel.add(serverField, constraints);
		
		resetConstraints(constraints);
		addSidePanel(panel);
		
   	}
   	
   	
	/** Creation method: chat text area and text field*/
   	private void addChatPanel() {
   		label12Chat = new JLabel("Chat");
		label12Chat.setBackground(new Color(150, 100, 150));
		
		chatArea = new JTextArea();
		chatArea.setText("Ingame chat");
		chatArea.setEditable(false);
		chatArea.setLineWrap(true);
		chatArea.setWrapStyleWord(true);
		chatArea.setLineWrap(true);
		chatArea.setWrapStyleWord(false);
		
		// just to contain the textarea, otherwise it will flood the screen when large messages appear
		JScrollPane chatScrollPane = new JScrollPane(chatArea);
		chatField = new JTextField("");
		chatField.addActionListener(new ActionSendChat(relayRef));
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.NONE;
		panel.add(label12Chat, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		panel.add(chatScrollPane, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 1;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.BOTH;
		panel.add(chatField, constraints);
		
		resetConstraints(constraints);
		addSidePanel(panel);
		
   	}
   	
   	private void addCardNotifyZone() {
   		Icon newcard = null;
   		try {
			newcard = new ImageIcon(FilesHelper.getResourceAsByteArray("resources/artwork/new.gif"));
		} catch (IOException e) {
			LOGGER.warning("Could not read resources/artwork/new.gif" + e.getMessage());
		}
   		label13CardNotify = new JLabel(newcard);
   		label13CardNotify.setHorizontalTextPosition(JLabel.LEFT);
		label13CardNotify.setVerticalTextPosition(JLabel.CENTER);
		label13CardNotify.setVisible(false);
   		constraints.gridx = 0;
		constraints.gridy = 14;
		constraints.weightx = left_panels;
		add(label13CardNotify, constraints);
		resetConstraints(constraints);
   	}
   	
	/** Creation method: button to show the object Cards in your possession*/
   	private void initializeButtons() { 
   		
		showCardsButton = new JButton("See your object Cards");
		showCardsButton.addActionListener(buttonHandler);
   		constraints.gridx = 1;
		constraints.gridy = 14;
		constraints.weightx = 1;
		add(showCardsButton, constraints);
		resetConstraints(constraints);
   	
   	}  	
   	
   	/**This method adds a panel to the area on the left side. note: It doesn't specify the Panel layout.
   	 * @param panel 	 */
   	private void addSidePanel(JPanel panel) {
   		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = left_panels;
		constraints.gridx = 0;
		constraints.gridy = currentRow;
		currentRow++;
		add(panel,constraints);
		resetConstraints(constraints);
   	}
	
	
   	
	/**This method receives an array of components and places them in a JPanel, 
	 * using a GridBagLayout that places all components in the same row. 
	 * @param List<JComponent> components	 */
	private JPanel createRowPanel(List<? extends JComponent> components) {
		JPanel panel = new JPanel();
		int currentColumn = 0;
		panel.setLayout(new GridBagLayout());
	
		for (JComponent c : components) {
			constraints.gridx = currentColumn;
			constraints.gridy = 0;
			constraints.weightx = 0.3;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			panel.add(c, constraints);
			currentColumn++;
		}
		return panel;
	}
	
	
	/**
	 * Reset a set of GridBagConstraints to a generic state.
	 * To be invoked after each time the constraints have been modified.
	 * @param constraint	 */
	private void resetConstraints(GridBagConstraints constraints) {
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridwidth = 1;	
		constraints.gridheight = 1;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		constraints.anchor = GridBagConstraints.CENTER;
	}
	
	private void setLabelsOpaque() {
		List<JLabel> labelsList = Arrays.asList(label2GameStatus,label3TurnNumber,
												label4,label5,label6,label7Map, label8, label9, label10, label12Chat);
		   for (JLabel l : labelsList){
			   l.setOpaque(true);
			}
	}
	
	
	
	
	private class ActionDisconnectButton implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			relayRef.disconnectNow();
			label0ServerStatus.setIcon(new ImageIcon(ImageScaler.resizeImage("resources/artwork/misc/stop.png", CONN_ICON_SIZE, CONN_ICON_SIZE)));
			label0ServerStatus.setToolTipText("Closed by user");
		}

	}

}
