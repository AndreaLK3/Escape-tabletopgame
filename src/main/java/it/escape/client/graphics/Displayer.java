package it.escape.client.graphics;

import it.escape.client.controller.Relay;
import it.escape.client.controller.gui.MouseOnMapCell;
import it.escape.client.controller.gui.UpdaterSwingToDisplayerInterface;
import it.escape.client.graphics.maplabel.MapViewer;
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


public class Displayer extends JFrame implements UpdaterSwingToDisplayerInterface{
   	private static final long serialVersionUID = 1L;
   	private static final int MAXPLAYERS = 8;
  	
   	private GridBagConstraints constraints;
   	
   	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JLabel label7;
	private JLabel label8;
	private JLabel label9;
	private JScrollPane mapScrollPane;
	
	private JTextField nameField;
	private JTextArea statusArea;
	private JTextArea teamArea;
	private JTextField chatField;
	private JTextArea chatArea;
	private JButton showCardsButton;
	
	private ObjectCardsPanel objectCardsPanel;
	private String chosenObjectCard;
	
	PlayerPanel playerPanels[] = new PlayerPanel[MAXPLAYERS];

	private Relay relayRef;
	
	NameListener myNameListener = new NameListener();
	ChatListener myChatListener = new ChatListener();
	ButtonHandler buttonHandler = new ButtonHandler();
	
	int currentRow = 1;
   	
	/**
	 * The constructor: initializes the window and all of its containers and components.
	 * @param string (the title of the window) 
	 * @param BindUpdaterInterface (updater that will feed us data from the net) 
	 * @param Relay (used to send data to the net)
	 */
   	public Displayer(String string, BindUpdaterInterface updater, Relay relay) {
   		super(string);
   		this.relayRef = relay;
   		updater.bindView(this);
   		setLayout(new GridBagLayout());
   		constraints = new GridBagConstraints();
   		
   		initializeGeneralLabels();
   		initializeMap();
   		initializeGeneralSidePanels();
   		initializePersonalPanels();
   		addChatPanel();
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
			
		label1 = new JLabel("Escape from the Aliens in Outer Space");
		label1.setBackground(new Color(248, 213, 131));
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
	    add(label1,constraints);
	    resetConstraints(constraints);
	
	    
		label2 = new JLabel("Players");
		label2.setBackground(new Color(200, 100, 200));
		
		label3 = new JLabel("Status");
		label3.setBackground(new Color(200, 100, 20));
		
		label4 = new JLabel("LastNoise");
		label4.setBackground(new Color(50, 100, 200));
		panel = createRowPanel(Arrays.asList(label2, label3, label4));
		addSidePanel(panel);
	
	}
	
	/** Creation method: the map, on the upper right part of the screen*/
	private void initializeMap() {

		try {
			label5 = new MapViewer();
		} catch (BadJsonFileException e) {
			label5 = new JLabel("Bad json map file");
		} catch (IOException e) {
			label5 = new JLabel("Cannot open map file");
		}
		((MapViewer)label5).addCellListener(new MouseOnMapCell((MapViewer)label5));
		mapScrollPane = new JScrollPane(label5);
		mapScrollPane.setPreferredSize(new Dimension(400,400)); 
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 12;
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
		
		statusArea = new JTextArea("Status");
		statusArea.setEditable(false);
		
		teamArea = new JTextArea("Team");
		teamArea.setEditable(false);
		
		panel = createRowPanel(Arrays.asList(nameField, statusArea, teamArea));
		addSidePanel(panel);
		
	}
   	
	/** Creation method: chat text area and text field*/
   	private void addChatPanel() {
   		label9 = new JLabel("Chat");
		label9.setBackground(new Color(150, 100, 150));
		
		chatArea = new JTextArea();
		chatArea.setText("Chat messages will be displayed here");
		chatArea.setEditable(false);
		
		chatField = new JTextField("You can write chat messages here");
		chatField.addActionListener(myChatListener);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.NONE;
		panel.add(label9, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		panel.add(chatArea, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 1;
		constraints.weighty = 1;
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
		constraints.gridy = 13;
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
		List<JLabel> labelsList = Arrays.asList(label1,label2,label3,label4,label5, label6, label7, label8, label9);
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

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == showCardsButton) {
				//the objectCardsPanel will have to be updated reading the client.model.PlayerState
				objectCardsPanel.updateCards(Arrays.asList("attack", "defense","teleport","lights","sedatives","adrenaline"));
				JOptionPane.showConfirmDialog(null, objectCardsPanel.getButtonsAsArray(), "Your object cards", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
				chosenObjectCard = objectCardsPanel.getChosenCardName();
				JOptionPane.showMessageDialog(null, "You have chosen the " + chosenObjectCard  + " card.");}
		}
		
	}
   	
   	public static void launch(final BindUpdaterInterface updater, final Relay relay) {
		EventQueue.invokeLater(
				new Runnable() {
					public void run() {
						Displayer playerFrame = new Displayer("Escape from the Aliens in Outer Space", updater, relay);	
					}
				}
		);
		}

   	
   	
   
   	


	
}
	
