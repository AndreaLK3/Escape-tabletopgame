package it.escape.core.client.view.gui;

import it.escape.core.client.controller.Relay;
import it.escape.core.client.controller.gui.ActionSendChat;
import it.escape.core.client.controller.gui.MouseOnMapCell;
import it.escape.core.client.controller.gui.NameListener;
import it.escape.core.client.controller.gui.UpdaterSwing;
import it.escape.core.client.model.GameStatus;
import it.escape.core.client.view.gui.maplabel.MapViewer;
import it.escape.core.server.model.game.exceptions.BadJsonFileException;
import it.escape.tools.utils.FilesHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import javax.swing.JOptionPane;
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
  	
	protected GridBagConstraints constraints;
   	
	protected JLabel labelServerStatus;
   	protected JLabel label0GameStatus;
   	protected JTextField gameStatusField;
   	protected JLabel label1TurnNumber;
   	protected JTextField turnNumberField;
   	protected JLabel label2;
   	protected JLabel label3;
   	protected JLabel label4;
   	protected JLabel label5Map;
   	protected JLabel label6;
   	protected JLabel label7;
	protected JLabel label8;
	protected JLabel label9TurnStatus;
	protected JLabel label10Chat;
	protected JLabel label11CardNotify;
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
	protected String chosenObjectCard;
	
	protected PlayerPanel playerPanels[] = new PlayerPanel[MAXPLAYERS];
	
	/**Note: this reference is made transient, since JFrame is serializable but the feature is not used. */
	protected transient NameListener myNameListener;
	protected transient ButtonHandler buttonHandler;
	protected transient Relay relayRef;
	
	protected boolean doRelayObjectCard;
	
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
   		buttonHandler = new ButtonHandler();
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
   		
   		objectCardsPanel = new ObjectCardsPanel();
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		setSize(950, 600);
		setVisible(true);
   	}
   	
   	/** Creation method: Labels in the upper part of the screen*/
	private void initializeGeneralLabels() {
		
		JPanel panel = new JPanel();
		
		buttonDisconnect = new JButton("Disconnect");
		buttonDisconnect.addActionListener(new ActionDisconnectButton());
		
		labelServerStatus = new JLabel(new ImageIcon(ImageScaler.resizeImage("resources/artwork/misc/check.png", CONN_ICON_SIZE, CONN_ICON_SIZE)));
		labelServerStatus.setText("Connection: ");
		labelServerStatus.setToolTipText("Online");
		labelServerStatus.setHorizontalTextPosition(JLabel.LEFT);
		labelServerStatus.setVerticalTextPosition(JLabel.CENTER);
		
		label0GameStatus = new JLabel("Game Status:");
		label0GameStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		gameStatusField = new JTextField(GameStatus.WAITING_FOR_PLAYERS.toString());
		gameStatusField.setEditable(false);
		
		label1TurnNumber = new JLabel("Turn Number:");
		label1TurnNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		turnNumberField = new JTextField("0");
		turnNumberField.setEditable(false);
		
		panel = createRowPanel(Arrays.asList(buttonDisconnect, labelServerStatus,
											label0GameStatus, gameStatusField, label1TurnNumber, turnNumberField));
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
	    add(panel,constraints);
	    resetConstraints(constraints);
	
	    
		label2 = new JLabel("Players");
		label2.setBackground(new Color(150, 130, 230));
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		
		label3 = new JLabel("Status");
		label3.setBackground(new Color(110, 220, 220));
		label3.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		label4 = new JLabel("LastLocation");
		label4.setBackground(new Color(50, 100, 200));
		label4.setHorizontalAlignment(SwingConstants.CENTER);
		panel = createRowPanel(Arrays.asList(label2, label3, label4));
		addSidePanel(panel);
	
	}
	
	/** Creation method: the map, on the upper right part of the screen*/
	protected void initializeMap() {

		try {
			label5Map = new MapViewer();
		} catch (BadJsonFileException e) {
			label5Map = new JLabel("Bad json map file");
			LOGGER.warning("Bad Json map file" + e.getMessage());
		} catch (IOException e) {
			label5Map = new JLabel("Cannot open map file");
			LOGGER.warning("Can not open map file" + e.getMessage());
		}
		((MapViewer)label5Map).addCellListener(new MouseOnMapCell((MapViewer)label5Map));
		mapScrollPane = new JScrollPane(label5Map);
		mapScrollPane.setPreferredSize(new Dimension(400,400));
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 1;
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
   		
		label6 = new JLabel("MyName");
		label6.setHorizontalAlignment(SwingConstants.CENTER);
		label6.setBackground(new Color(150, 200, 240));
		
		label7 = new JLabel("Status");
		label7.setHorizontalAlignment(SwingConstants.CENTER);
		label7.setBackground(new Color(180, 130, 240));
		
		label8 = new JLabel("Team");
		label8.setHorizontalAlignment(SwingConstants.CENTER);
		label8.setBackground(new Color(150, 150, 220));
		panel = createRowPanel(Arrays.asList(label6, label7, label8));
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
   		label9TurnStatus = new JLabel("Turn Status");
   		label9TurnStatus.setBackground(new Color(150, 100, 150));
		
   		serverField = new JTextField();
   		serverField.setText("Waiting for the game to start");
   		serverField.setEditable(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.NONE;
		panel.add(label9TurnStatus, constraints);
		
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
   		label10Chat = new JLabel("Chat");
		label10Chat.setBackground(new Color(150, 100, 150));
		
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
		panel.add(label10Chat, constraints);
		
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
   		label11CardNotify = new JLabel(newcard);
   		label11CardNotify.setHorizontalTextPosition(JLabel.LEFT);
		label11CardNotify.setVerticalTextPosition(JLabel.CENTER);
		label11CardNotify.setVisible(false);
   		constraints.gridx = 0;
		constraints.gridy = 14;
		constraints.weightx = 1;
		add(label11CardNotify, constraints);
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
		constraints.weightx = 0.4;
		constraints.gridx = 0;
		constraints.gridy = currentRow;
		currentRow++;
		add(panel,constraints);
		resetConstraints(constraints);
   	}
	
	
   	
	/**This method receives an array of components and places them in a JPanel, 
	 * using a GridLayout that places all components in the same row, filling the row and giving them the same space. 
	 * @param List<JComponent> components	 */
	private JPanel createRowPanel(List<? extends JComponent> components) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout());
		for (JComponent c : components) {
			panel.add(c);
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
		List<JLabel> labelsList = Arrays.asList(label0GameStatus,label1TurnNumber,
												label2,label3,label4,label5Map, label6, label7, label8, label10Chat);
		   for (JLabel l : labelsList){
			   l.setOpaque(true);
			}
	}
	
	
	/**This private inner class listens to the JButton ShowCardsButton.
	 * 1) When the user clicks it, it shows a dialog with objectCardsPanel,
	 * which contains the object Cards currently owned.
	 * 2) When the Updater "clicks" it, because the ServerSocketCore requires an Object Card, 
	 * it shows a dialog with the playable objectCards, and,
	 * depending on the user's choice, a String with the CardName is obtained and sent to the Relay.
	 * @author andrea, michele*/
	private class ButtonHandler implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == showCardsButton) {
				new Thread(
					new Runnable() {
						public void run() {
							if (doRelayObjectCard) {
								chosenObjectCard = null;
								do {
								JOptionPane.showConfirmDialog(null, objectCardsPanel.getPlayableButtonsAsArray(), 
										"Your object cards", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
								chosenObjectCard = objectCardsPanel.getChosenCardName();
								if (chosenObjectCard == null){
									JOptionPane.showMessageDialog(null, "You haven't chosen any card.");
								}
								else {
									JOptionPane.showMessageDialog(null, "You have chosen the " + chosenObjectCard  + " card.");
								}
								}while(chosenObjectCard == null);	//note: the user must choose a valid object card name
																			//(even an unplayable one).
								relayRef.relayMessage(chosenObjectCard);	//This line sends the name of the chosen objectCard to the ServerSocketCore
								doRelayObjectCard = false;					//This line resets the variable.
							}
							else {
								JOptionPane.showConfirmDialog(null, objectCardsPanel.getButtonsAsArray(), 
											"Your object cards", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
							}
							}
						}).start();
			}
		}
	}
	
	private class ActionDisconnectButton implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			relayRef.disconnectNow();
			labelServerStatus.setIcon(new ImageIcon(ImageScaler.resizeImage("resources/artwork/misc/stop.png", CONN_ICON_SIZE, CONN_ICON_SIZE)));
			labelServerStatus.setToolTipText("Closed by user");
		}

	}

}
