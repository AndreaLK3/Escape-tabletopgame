package it.escape.client.view.gui;

import it.escape.client.controller.MessageCarrier;
import it.escape.client.controller.Relay;
import it.escape.client.controller.gui.ActionSendChat;
import it.escape.client.controller.gui.MouseOnMapCell;
import it.escape.client.controller.gui.UpdaterSwingToDisplayerInterface;
import it.escape.client.model.ModelForGUI;
import it.escape.client.view.gui.maplabel.MapViewer;
import it.escape.server.model.game.exceptions.BadJsonFileException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
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
public class SwingView extends JFrame implements UpdaterSwingToDisplayerInterface, Observer{
   	private static final long serialVersionUID = 1L;
   	private static final int MAXPLAYERS = 8;
  	
   	private GridBagConstraints constraints;
   	
   	private JLabel label1_title;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5_map;
	private JLabel label6;
	private JLabel label7;
	private JLabel label8;
	private JLabel label9_turnStatus;
	private JLabel label10_chat;
	private JScrollPane mapScrollPane;
	
	private JTextField nameField;
	private JTextField statusArea;
	private JTextField teamArea;
	JTextArea serverArea;
	private JTextField chatField;
	private JTextArea chatArea;
	private JButton showCardsButton;
	
	private ObjectCardsPanel objectCardsPanel;
	private String chosenObjectCard;
	
	PlayerPanel playerPanels[] = new PlayerPanel[MAXPLAYERS];

	private Relay relayRef;
	
	NameListener myNameListener = new NameListener();
	ButtonHandler buttonHandler = new ButtonHandler();
	
	int currentRow = 1;
   	
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
   		setLayout(new GridBagLayout());
   		constraints = new GridBagConstraints();
   		
   		initializeGeneralLabels();
   		initializeMap();
   		initializeGeneralSidePanels();
   		initializePersonalPanels();
   		addTurnStatusPanel();
   		addChatPanel();
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
			
		label1_title = new JLabel("Escape from the Aliens in Outer Space");
		label1_title.setBackground(new Color(248, 213, 131));
		label1_title.setHorizontalAlignment(SwingConstants.CENTER);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
	    add(label1_title,constraints);
	    resetConstraints(constraints);
	
	    
		label2 = new JLabel("Players");
		label2.setBackground(new Color(200, 100, 200));
		
		label3 = new JLabel("Status");
		label3.setBackground(new Color(200, 100, 20));
		
		label4 = new JLabel("LastKnownAction");
		label4.setBackground(new Color(50, 100, 200));
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
		label6.setBackground(new Color(20, 100, 20));
		
		label7 = new JLabel("Status");
		label7.setBackground(new Color(200, 100, 20));
		
		label8 = new JLabel("Team");
		label8.setBackground(new Color(50, 100, 200));
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
		
   		serverArea = new JTextArea();
		serverArea.setText("Waiting for the game to start");
		serverArea.setEditable(false);
		JScrollPane serverScrollPane = new JScrollPane(serverArea);
		
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
		panel.add(serverScrollPane, constraints);
		
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
	 * using a GridBagLayout that places all components in the same row. 
	 * Overloaded version works with an Array.
	 * @param List<JComponent> components
	 */
	private JPanel createRowPanel(List<? extends JComponent> components) {
		int column=0;
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		for (JComponent c : components) {
			constraints.gridx=column;
			constraints.gridy=0;
			constraints.weightx = 1;
			panel.add(c, constraints);
			column++;
			resetConstraints(constraints);
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
		List<JLabel> labelsList = Arrays.asList(label1_title,label2,label3,label4,label5_map, label6, label7, label8, label10_chat);
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
	
	private class ButtonHandler implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == showCardsButton) {
				//the objectCardsPanel will have to be updated reading the client.model.PlayerState
				objectCardsPanel.updateCards(Arrays.asList("attack", "defense","teleport","lights","sedatives","adrenaline"));
				JOptionPane.showConfirmDialog(null, objectCardsPanel.getButtonsAsArray(), "Your object cards", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
				chosenObjectCard = objectCardsPanel.getChosenCardName();
				JOptionPane.showMessageDialog(null, "You have chosen the " + chosenObjectCard  + " card.");}
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
   	
   	public void updateMyStatusScreen(ModelForGUI model) {
   		nameField.setText(model.getMyPlayerState().getMyName());
   		teamArea.setText(model.getMyPlayerState().getMyTeam());
   	}
   	
   	public void updateMapMarkers(ModelForGUI model) {
   		((MapViewer) label5_map).setPlayerMarkerPosition(model.getMyPlayerState().getLocation());
   	}
   	
   	// basic observer (it observes the model)
   	public void update(Observable arg0, Object arg1) {
   		if (arg0 instanceof ModelForGUI) {
   			ModelForGUI model = (ModelForGUI) arg0;
   			updateMyStatusScreen(model);
   			updateMapMarkers(model);
			// do something
		}
	}
   	
   	// functions belonging to the interface UpdaterSwingToDisplayerInterface
   	//(They are invoked by the UpdaterSwing)

	public void setGameMap(String name) {
		try {
			((MapViewer)label5_map).setMap(name);
			scrollMap(0.5, 0.5);
			((MapViewer)label5_map).setPlayerMarkerPosition("L08");
		} catch (BadJsonFileException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
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
		serverArea.setText(status);
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
}
	
