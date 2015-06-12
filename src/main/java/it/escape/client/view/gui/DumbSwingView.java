package it.escape.client.view.gui;

import it.escape.client.controller.Relay;
import it.escape.client.controller.gui.ActionSendChat;
import it.escape.client.controller.gui.MouseOnMapCell;
import it.escape.client.controller.gui.NameListener;
import it.escape.client.model.GameStatus;
import it.escape.client.view.gui.maplabel.MapViewer;
import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.utils.FilesHelper;

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
 * 2) Attaching the relay to said cards-dialog and to the chat textfield
 * 
 * The class is made abstract, because it's useless on its own and should not be
 * directly instantiated
 * @author andrea, michele
 */
public abstract class DumbSwingView extends JFrame {
	private static final long serialVersionUID = 1L;
	protected static final int MAXPLAYERS = 8;
  	
	protected GridBagConstraints constraints;
   	
   	protected JLabel label0_gameStatus;
   	protected JTextField gameStatusField;
   	protected JLabel label1_turnNumber;
   	protected JTextField turnNumberField;
   	protected JLabel label2;
   	protected JLabel label3;
   	protected JLabel label4;
   	protected JLabel label5_map;
   	protected JLabel label6;
   	protected JLabel label7;
	protected JLabel label8;
	protected JLabel label9_turnStatus;
	protected JLabel label10_chat;
	protected JLabel label11_card_notify;
	protected JScrollPane mapScrollPane;
	
	protected JTextField nameField;
	protected JTextField statusArea;
	protected JTextField teamArea;
	protected JTextField serverField;
	protected JTextField chatField;
	protected JTextArea chatArea;
	protected JButton showCardsButton;
	
	protected ObjectCardsPanel objectCardsPanel;
	protected String chosenObjectCard;
	
	protected PlayerPanel playerPanels[] = new PlayerPanel[MAXPLAYERS];
	
	protected NameListener myNameListener;
	protected ButtonHandler buttonHandler;
	protected Relay relayRef;
	
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
   		setSize(800, 600);
		setVisible(true);
   	}
   	
   	
  

   	/** Creation method: Labels in the upper part of the screen*/
	private void initializeGeneralLabels() {
		
		JPanel panel = new JPanel();
		
		label0_gameStatus = new JLabel("Game Status:");
		label0_gameStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		gameStatusField = new JTextField(GameStatus.WAITING_FOR_PLAYERS.toString());
		gameStatusField.setEditable(false);
		
		label1_turnNumber = new JLabel("Turn Number:");
		label1_turnNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		turnNumberField = new JTextField("0");
		turnNumberField.setEditable(false);
		
		panel = createRowPanel(Arrays.asList(label0_gameStatus, gameStatusField, label1_turnNumber, turnNumberField));
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
			label5_map = new MapViewer();
		} catch (BadJsonFileException e) {
			label5_map = new JLabel("Bad json map file");
		} catch (IOException e) {
			label5_map = new JLabel("Cannot open map file");
		}
		((MapViewer)label5_map).addCellListener(new MouseOnMapCell((MapViewer)label5_map));
		mapScrollPane = new JScrollPane(label5_map);
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
		label7.setBackground(new Color(180, 130, 240));;
		
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
   		label9_turnStatus = new JLabel("Turn Status");
   		label9_turnStatus.setBackground(new Color(150, 100, 150));
		
   		serverField = new JTextField();
   		serverField.setText("Waiting for the game to start");
   		serverField.setEditable(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.NONE;
		panel.add(label9_turnStatus, constraints);
		
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
   		label10_chat = new JLabel("Chat");
		label10_chat.setBackground(new Color(150, 100, 150));
		
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
		panel.add(label10_chat, constraints);
		
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
		}
   		label11_card_notify = new JLabel(newcard);
   		label11_card_notify.setHorizontalTextPosition(JLabel.LEFT);
		label11_card_notify.setVerticalTextPosition(JLabel.CENTER);
		label11_card_notify.setVisible(false);
   		constraints.gridx = 0;
		constraints.gridy = 14;
		constraints.weightx = 1;
		add(label11_card_notify, constraints);
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
		List<JLabel> labelsList = Arrays.asList(label0_gameStatus,label1_turnNumber,label2,label3,label4,label5_map, label6, label7, label8, label10_chat);
		   for (JLabel l : labelsList){
			   l.setOpaque(true);
			}
	}
	
	
	private PlayerPanel findPlayerPanel(String playerName) {
		for (PlayerPanel p : playerPanels) {
			if (playerName == p.getPlayer())
				return p;
		}
		return null;
			
	}
	
	/**This private inner class listens to the JButton ShowCardsButton.
	 * 1) When the user clicks it, it shows a dialog with objectCardsPanel,
	 * which contains the object Cards currently owned.
	 * 2) When the Updater "clicks" it, because the Server requires an Object Card, 
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
								JOptionPane.showConfirmDialog(null, objectCardsPanel.getPlayableButtonsAsArray(), 
										"Your object cards", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
								chosenObjectCard = objectCardsPanel.getChosenCardName();
								if (chosenObjectCard==null){
									JOptionPane.showMessageDialog(null, "You haven't chosen any card.");
								}
								else {
									JOptionPane.showMessageDialog(null, "You have chosen the " + chosenObjectCard  + " card.");
									relayRef.relayMessage(chosenObjectCard);	
								}
								objectCardsPanel.getGroup().clearSelection();
								chosenObjectCard = null;
								doRelayObjectCard = false;
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

}
