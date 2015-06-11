package it.escape.client.view.gui;

import it.escape.client.ClientInitializerGUI;
import it.escape.client.controller.MessageCarrier;
import it.escape.client.controller.Relay;
import it.escape.client.controller.gui.ActionSendChat;
import it.escape.client.controller.gui.ClickSendPositionListener;
import it.escape.client.controller.gui.MouseOnMapCell;
import it.escape.client.controller.gui.UpdaterSwingToViewInterface;
import it.escape.client.model.GameStatus;
import it.escape.client.model.ModelForGUI;
import it.escape.client.model.PlayerState;
import it.escape.client.view.gui.maplabel.MapViewer;
import it.escape.server.model.game.exceptions.BadCoordinatesException;
import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.strings.StringRes;
import it.escape.utils.FilesHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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

import sun.misc.Lock;

/**This class contains the Frame that is displayed for the Client that uses the GUI.
 * Responsibilities:
 * 1) Containing all the components
 * 2) Creating and initializing the components (n: this one could be transferred to another class)
 * 3) Observes PlayerStates/PlayerStateHandler and GameState, and is updated accordingly
 * 4) Can send messages to the Relay (ex: -chat messages , -send input obtained through dialogs)
 * 
 * @author andrea, michele
 */
public class SwingView extends JFrame implements UpdaterSwingToViewInterface, Observer{
   	private static final long serialVersionUID = 1L;
   	private static final int MAXPLAYERS = 8;
  	
   	private GridBagConstraints constraints;
   	
   	private JLabel label0_gameStatus;
   	private JTextField gameStatusField;
   	private JLabel label1_turnNumber;
   	private JTextField turnNumberField;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5_map;
	private JLabel label6;
	private JLabel label7;
	private JLabel label8;
	private JLabel label9_turnStatus;
	private JLabel label10_chat;
	private JLabel label11_card_notify;
	private JScrollPane mapScrollPane;
	
	private JTextField nameField;
	private JTextField statusArea;
	private JTextField teamArea;
	private JTextField serverField;
	private JTextField chatField;
	private JTextArea chatArea;
	private JButton showCardsButton;
	
	private ObjectCardsPanel objectCardsPanel;
	private String chosenObjectCard;
	
	PlayerPanel playerPanels[] = new PlayerPanel[MAXPLAYERS];

	private Relay relayRef;
	
	NameListener myNameListener;
	ButtonHandler buttonHandler;
	
	int currentRow = 1;
	
	private boolean doRelayObjectCard;
	
   	
	/**
	 * The constructor: initializes the window and all of its containers and components.
	 * @param string (the title of the window) 
	 * [@param BindUpdaterInterface (updater that will feed us data from the net) 
	 * note: removed, since we don't need this connection (see Client Diagram), and it creates a cycle too]
	 * @param Relay (used to send data to the net)
	 */
   	public SwingView(String string, BindUpdaterInterface updater, Relay relay, Observable model) {
   		super(string);
   		this.relayRef = relay;
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
   		
   		updater.bindView(this);
   		model.addObserver(this);
   		
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
	
	/**
	 * Programmatically scroll the map panel to a specified position
	 * (i.e. scrollMap(0.5, 0.5) centers the map)
	 * @param x 0 - 1 position of the center of the horizontal slider
	 * @param y 0 - 1 position of the center of the vertical slider
	 */
	private void scrollMap(double x, double y) {
		// scrollbar ranges (in "scroll-pixels")
		int maxWidth = mapScrollPane.getHorizontalScrollBar().getMaximum();
		int maxHeight = mapScrollPane.getVerticalScrollBar().getMaximum();
		// unrolled map size
		int mapWidth = ((MapViewer)label5_map).getTotalWidth();
		int mapHeight = ((MapViewer)label5_map).getTotalHeight();
		// visible map size
		int viewWidth = mapScrollPane.getWidth();
		int viewHeight = mapScrollPane.getHeight();
		
		// size of the visible map in "scroll-pixels"
		int relativeWidth = (viewWidth * maxWidth) / mapWidth;
		int relativeHeight = (viewHeight * maxHeight) / mapHeight;
		
		// scroll the panel so that both sliders are perfectly centered on the specified positions
		mapScrollPane.getHorizontalScrollBar().setValue((int)Math.round(maxWidth * x) - relativeWidth/2);
		mapScrollPane.getVerticalScrollBar().setValue((int)Math.round(maxHeight * y) - relativeHeight/2);
		
	}
	
	private void focusOnLocationInstantly(String coord) {
		try {
			int pos[] = ((MapViewer)label5_map).cellToPixels(CoordinatesConverter.fromAlphaNumToOddq(coord));
			int correct_X = ((MapViewer)label5_map).getCellWidth() / 2;
			int correct_Y = ((MapViewer)label5_map).getCellHeight() / 2;
			int map_x = ((MapViewer)label5_map).getTotalWidth();
			int map_y = ((MapViewer)label5_map).getTotalHeight();
			scrollMap((double) (pos[0] + correct_X) / map_x, (double) (pos[1] + correct_Y) / map_y);
		} catch (BadCoordinatesException e) {
		}
	}
	
	/** Creation method: the map, on the upper right part of the screen*/
	private void initializeMap() {

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
		int column=0;
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
	
	
	
   	
   	public static void synchronousLaunch(final BindUpdaterInterface updater, final Relay relay, final Observable model) {
   		final Lock l = new Lock();
   		try {
			l.lock();  // (1) set mutex once, so that the program flow will stop at (2)
		} catch (InterruptedException e1) {
		}
   		
		EventQueue.invokeLater(
				new Runnable() {
					public void run() {
						SwingView playerFrame = new SwingView("Escape from the Aliens in Outer Space", updater, relay, model);	
						l.unlock();  // unlock the mutex, let the synchronousLaunch return
					}
				}
		);
		try {
			l.lock();  // (2) try again setting the mutex, but it must be unlocked first
		} catch (InterruptedException e) {
		}
		
		}
   	
   	/**This method updates the current GameStatus and TurnNumber*/
   	private void updateGameStatePanel(ModelForGUI model) {
   		gameStatusField.setText(model.getGameStatus().toString());
   		turnNumberField.setText(""+model.getTurnNumber());
   	}
   	
   	/** This method updates my personal panel
   	 * TODO: TurnStatus*/
   	private void updateMyStatusScreen(ModelForGUI model) {
   		nameField.setText(model.getMyPlayerState().getMyName());
   		teamArea.setText(model.getMyPlayerState().getMyTeam());
   	}
   	
   	/**This method updates the position of the player icon on the map*/
   	private void updateMapMarkers(ModelForGUI model) {
   		((MapViewer) label5_map).setPlayerMarkerPosition(model.getMyPlayerState().getLocation());
   	}
   	
   	/**This method, depending on the info that are stored in the model,
   	 * calls the method updateCards inside the objectCards panel, 
   	 * to update the List of JRadioButtons that correspond to the cards*/
   	private void updateObjectCardsPanel(ModelForGUI model) {
   		objectCardsPanel.updateCards(model.getMyPlayerState().getObjectCards());
   	}
   	
   	
   	private void updatePlayerPanels(ModelForGUI model) {
   		int currentPanel = 0;
   		for (PlayerState pState : model.getPlayerStates()) {
   			playerPanels[currentPanel].updatePlayerArea(pState.getMyName());
   			playerPanels[currentPanel].updateStatusArea(pState.getMyStatus().toString());
   			playerPanels[currentPanel].updateLastKnownActionArea(pState.getLastNoiseLocation());
   			currentPanel++;
   		}
   	}
   	
   /**This method observes the model; upon any model changes, it 
    * invokes the methods that update this View according to the data stored in the Model */
   	public void update(Observable arg0, Object arg1) {
   		if (arg0 instanceof ModelForGUI) {
   			ModelForGUI model = (ModelForGUI) arg0;
   			updateMyStatusScreen(model);
   			updatePlayerPanels(model);
   			updateMapMarkers(model);
   			updateObjectCardsPanel(model);
   			updateGameStatePanel(model);
		}
	}
   	
   	// functions belonging to the interface UpdaterSwingToViewInterface
   	//(They are invoked by the UpdaterSwing)

	public void setGameMap(final String name) {
		EventQueue.invokeLater(
				new Runnable() {
					public void run() {
						try {
							((MapViewer)label5_map).setMap(
									name,
									new Runnable() { public void run() {
										try {
											Thread.sleep(100);
										} catch (InterruptedException e) {
										}
										scrollMap(0.5, 0.5);}}
									);
						} catch (BadJsonFileException e) {
							
						} catch (IOException e) {
							
						} 
					}});
	}

	/**This method uses a new thread to show the welcoming Dialog. */
	public void displayServerMOTD(final String motd) {
		new Thread(new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog(null, 
					    motd,
					    "Welcome!",
					    JOptionPane.PLAIN_MESSAGE);
			}
		}).start();
	}

	public void setTurnStatusString(String status) {
		serverField.setText(status);
	}
	
	public void notifyNewCard(String cardName) {
		final int duration = 10000;
		label11_card_notify.setText("You have drawn a new " + cardName + " card");
		label11_card_notify.setVisible(true);
		new Thread(
			new Runnable() {
				public void run() {
					try {
						Thread.sleep(duration);
					} catch (InterruptedException e) {
					}
					label11_card_notify.setVisible(false);
			}}).start();
	}

	/** This method receives a chat message, and it displays it inside
	 * the chat TextArea appended after the previous messages.
	 */
	public void newChatMessage(String username, String message) {
		String oldChatText = chatArea.getText();
		StringBuilder newChatText = new StringBuilder();
		if (oldChatText.length() > 0) {
			newChatText.append(oldChatText);
			newChatText.append("\n");
		}
		newChatText.append(username + ": ");
		newChatText.append(message);
		chatArea.setText(newChatText.toString());
		// TODO: magari aggiungere un'icona 'new message' da qualche parte
	}

	public void discoverMyName() {
		relayRef.sendWhoami();
	}

	public void bindPositionSender() {
		ClickSendPositionListener listener = new ClickSendPositionListener(relayRef, this);
		((MapViewer)label5_map).addCellListener(listener);
	}
	
	public void unbindPositionSender(ClickSendPositionListener listener) {
		((MapViewer)label5_map).removeCellListener(listener);
	}



	public void relayYesNoDialog(final String question) {
		new Thread(
			new Runnable() {
				public void run() {
					int n = JOptionPane.showConfirmDialog(null, question, null, JOptionPane.YES_NO_OPTION);
					if (n==JOptionPane.OK_OPTION) {
						relayRef.relayMessage("yes");
					}
					else {
						relayRef.relayMessage("no");
					}
				}}).start();
		
	}
	
	/**Overloaded version of the binary dialog, using custom options instead of yes/no*/
	public void relayYesNoDialog(final String question, final String option1, final String option2) {
		new Thread(
			new Runnable() {
				public void run() {
					String options[] = {option1, option2};
					int n = JOptionPane.showOptionDialog
							(null, question, null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);				
					if (n==JOptionPane.YES_OPTION) {
						relayRef.relayMessage(option1);
					}
					else {
						relayRef.relayMessage(option2);
					}
				}}).start();
		
	}

	/**This method causes a pop-up (messageDialog) that shows some message to the user
	 * @param String message*/
	public void notifyUser(final String message) {
		new Thread(
				new Runnable() {
					public void run() {
						JOptionPane.showMessageDialog(null, message, null, JOptionPane.PLAIN_MESSAGE);
					}}).start();
	}

	/**This method is invoked by the UpdaterSwing when the Server requires 
	 * the name of an ObjectCard*/
	public void relayObjectCard() { 
		doRelayObjectCard = true;
		showCardsButton.doClick();
	}

	public void addNoiseToMap(String location) {
		((MapViewer)label5_map).addNoiseMarker(location);
	}
	public void clearNoisesFromMap() {
		((MapViewer)label5_map).clearNoiseMarkers();
	}
	public void addOtherPlayerToMap(String location, String name) {
		((MapViewer)label5_map).addOtherPlayerMarker(location, name);
	}
	public void removeOtherPlayerFromMap(String name) {
		((MapViewer)label5_map).removeSpecificPlayer(name);
	}
	public void clearOtherPlayersFromMap() {
		((MapViewer)label5_map).clearOtherPlayerMarkers();
	}
	public void focusOnLocation(final String coord, final int waitBefore) {
		new Thread(
				new Runnable() {
					public void run() {
						try {
							Thread.sleep(waitBefore);
						} catch (InterruptedException e) {
						}
						focusOnLocationInstantly(coord);
					}}).start();
	}
	
	
}
	
