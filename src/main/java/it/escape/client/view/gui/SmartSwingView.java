package it.escape.client.view.gui;

import it.escape.client.BindUpdaterInterface;
import it.escape.client.connection.BindDisconnectCallbackInterface;
import it.escape.client.connection.DisconnectedCallbackInterface;
import it.escape.client.controller.Relay;
import it.escape.client.controller.gui.ClickSendPositionListener;
import it.escape.client.controller.gui.UpdaterSwingToViewInterface;
import it.escape.client.model.ModelForGUI;
import it.escape.client.model.PlayerState;
import it.escape.client.model.VictoryState;
import it.escape.client.view.gui.maplabel.MapViewer;
import it.escape.server.model.game.exceptions.BadCoordinatesException;
import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This is the only subclass of DumbSwingView;
 * This class carries on all the 'active' duties:
 * 1) Biding the view to the updater
 * 2) Observing the model
 * 3) Providing an interface for the Updater to manipulate
 *    the view's appearance
 * 4) Reacting to changes in the model's data
 * 
 * This is the 'real' SwingView class, an external caller would
 * want to instantiate *this*
 * @author michele, andrea
 *
 */
public class SmartSwingView extends DumbSwingView implements UpdaterSwingToViewInterface, Observer, DisconnectedCallbackInterface{

	private static final long serialVersionUID = 1L;
	
	private ReentrantLock finalPhase;
	private GridBagConstraints constraints;

	/**
	 * The constructor: initializes the window and all of its containers and components.
	 * @param string (the title of the window) 
	 * [@param BindUpdaterInterface (updater that will feed us data from the net) 
	 * note: removed, since we don't need this connection (see Client Diagram), and it creates a cycle too]
	 * @param Relay (used to send data to the net)
	 */
   	public SmartSwingView(String string, Relay relay, ReentrantLock finalPhase) {
   		super(string, relay);
   		this.finalPhase = finalPhase;
   	}
   	
   	/**
   	 * To be called when we have finished all our work,
   	 * including displaying the winners.
   	 * It allows the program to stop when the connection
   	 * falls.
   	 */
   	private void allowQuit() {
   		finalPhase.unlock();
   	}
   	
	/**
	 * Called when a server-initiated disconnect is detected
	 */
	public void disconnected() {
		label_serverStatus.setIcon(new ImageIcon(ImageScaler.resizeImage("resources/artwork/misc/wrong.png", CONN_ICON_SIZE, CONN_ICON_SIZE)));
		label_serverStatus.setToolTipText("Closed by remote");
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

	public static void synchronousLaunch(final BindUpdaterInterface updater, 
			final Relay relay, final Observable model, final ReentrantLock finalPhase, 
			final BindDisconnectCallbackInterface connection) {
   		final ReentrantLock l = new ReentrantLock();
   		// launch phase
		EventQueue.invokeLater(
				new Runnable() {
					public void run() {
						l.lock();  // (1) set mutex once, so that the program flow will stop at (2)
						finalPhase.lock();  // this other mutex will prevent the program from quitting when the connection frops
						SmartSwingView view = new SmartSwingView("Escape from the Aliens in Outer Space", relay, finalPhase);	
						updater.bindView(view);
				   		model.addObserver(view);
				   		connection.bindDisconnCallback(view);
						l.unlock();  // unlock the mutex, let the synchronousLaunch return
					}
				}
		);
		/*
		 * synchronization phase.
		 * We need another thread, because reentrantLock's lock() will
		 * not work if the lock is held by the same thread (we're still in EDT)
		 */
		
		Thread sync = new Thread(new Runnable() {
			public void run() {
				l.lock();  // (2) try again setting the mutex, but it must be unlocked first
			}
		});
		sync.start();
		try {
			sync.join();
		} catch (InterruptedException e) {
		}
	}
   	
   	/**This method updates the current GameStatus and TurnNumber*/
   	private void updateGameStatePanel(ModelForGUI model) {
   		gameStatusField.setText(model.getGameStatus().toString());
   		turnNumberField.setText(""+model.getTurnNumber());
   	}
   	
   	/** This method updates my personal panel*/
   	private void updateMyStatusScreen(ModelForGUI model) {
   		nameField.setText(model.getMyPlayerState().getMyName());
   		statusArea.setText(model.getMyPlayerState().getMyStatus().toString());
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

	/**
	 * Spawn a dialog / panel / whatever to show the results of the match
	 * The dialog is spawned from inside the EDT, so that the application
	 * won't instantly stop.
	 * The model is passed as an argument
	 */
	public void spawnVictoryRecap(final ModelForGUI model) {
		EventQueue.invokeLater(
			new Runnable() {
				public void run() {
					JPanel resultsPanel = createResultsPanel(model);
					JOptionPane.showMessageDialog
						(null, resultsPanel, "End of the Game! Here are the results", JOptionPane.INFORMATION_MESSAGE);
				} 
			});
	}
	
	/**This method creates panels with the final results for each team, depending on the results in VictoryState*/
	public JPanel createResultsPanel(ModelForGUI model) {
		JPanel resultsPanel = new JPanel();
		VictoryState finalGameState= model.getVictoryState();
		GridBagConstraints panelConstraints = new GridBagConstraints();
		
		TeamVictoryPanel panelHumans = new TeamVictoryPanel();
		panelHumans.initializeTeamPanel("Humans");
		panelHumans.fillVictoryPanel(finalGameState.isHumansDefeated(), finalGameState.getHumanWinners());
		
		TeamVictoryPanel panelAliens = new TeamVictoryPanel();
		panelAliens.initializeTeamPanel("Aliens");
		panelAliens.fillVictoryPanel(finalGameState.isAliensDefeated(), finalGameState.getAlienWinners());
		
		resultsPanel.setLayout(new GridBagLayout());
		panelConstraints.gridx=0;
		panelConstraints.gridy=0;
		resultsPanel.add(panelHumans, panelConstraints);
		panelConstraints.gridx=1;
		panelConstraints.gridy=0;
		resultsPanel.add(panelAliens, panelConstraints);
		return resultsPanel;
	}
	
}
