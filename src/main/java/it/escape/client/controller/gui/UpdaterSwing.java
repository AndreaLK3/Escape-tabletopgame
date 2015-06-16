package it.escape.client.controller.gui;

import it.escape.client.BindUpdaterInterface;
import it.escape.client.controller.MessageCarrier;
import it.escape.client.controller.Updater;
import it.escape.client.model.CurrentPlayerStatus;
import it.escape.client.model.GameStatus;
import it.escape.client.model.ModelForGUI;
import it.escape.strings.FormatToPattern;
import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Updates the GUIModel and/or sends commands to the GUIView when a new network message arrives
 * @author michele, andrea
 */
public class UpdaterSwing extends Updater implements Observer, BindUpdaterInterface, ClientProceduresInterface {
	
	protected static final Logger LOG = Logger.getLogger( UpdaterSwing.class.getName() );

	private UpdaterSwingToViewInterface view;
	
	private boolean readingMotd = false;
	
	private ModelForGUI model;
	
	private String loadedMotd = "";
	
	private Pattern info_numLobbyPlayers;
	private Pattern info_playerConnected;
	private Pattern info_playerDisconnected;
	private Pattern info_yourTeam;
	private Pattern info_currentTurnAndPlayer;
	private Pattern info_playerRenamed;
	private Pattern info_drawnObjectCard;
	private Pattern info_whoIAm;
	private Pattern info_whereIAm;
	private Pattern info_discardedCard;
	private Pattern info_teamWinners;
	private Pattern info_teamDefeated;
	
	private Pattern turn_askForAttack;
	private Pattern turn_askForNoisePos;
	private Pattern turn_movement;
	private Pattern turn_askForObject;
	private Pattern turn_askForLightsPos;
	private Pattern turn_discard;
	private Pattern turn_escaped;
	private Pattern turn_failedEscape;
	
	private Pattern event_Noise;
	private Pattern event_ObjectUsed;
	private Pattern event_PlayerLocated;
	private Pattern event_Attack;
	private Pattern event_Death;
	private Pattern event_Defense;
	private Pattern event_EndGame;
	private Pattern event_EndOfResults;
	
	private Pattern event_PlayerEscaped;
	
	private Pattern exception_1;
	private Pattern exception_2;
	private Pattern exception_3;

	


	

	public UpdaterSwing(ModelForGUI model) {
		super();
		LogHelper.setDefaultOptions(LOG);
		this.model = model;
	}

	public void bindView(UpdaterSwingToViewInterface view) {
		this.view = view;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof MessageCarrier) {
			MessageCarrier msg = (MessageCarrier) arg0;
			try {
				processMessage(msg.getMessage());
			} catch (RemoteException e) {
				LOG.severe("RemoteException was thrown while RMI was NOT supposed to be running: " + e.getMessage());
			}
		}
	}
	
	
	protected void initPatterns() {
		super.initPatterns();
		
		info_numLobbyPlayers = new FormatToPattern(StringRes.getString("messaging.othersWaiting")).convert();
		info_playerConnected = new FormatToPattern(StringRes.getString("messaging.playerConnected")).convert();
		info_playerDisconnected =  new FormatToPattern(StringRes.getString("messaging.playerDisconnected")).convert();
		info_yourTeam = new FormatToPattern(StringRes.getString("messaging.gamemaster.playAs")).convert();
		info_currentTurnAndPlayer = new FormatToPattern(StringRes.getString("messaging.timecontroller.turnNumber")).convert();
		info_drawnObjectCard = new FormatToPattern(StringRes.getString("messaging.objectCardDrawn")).convert();
		info_playerRenamed  = new FormatToPattern(StringRes.getString("messaging.announceRename")).convert();
		info_whoIAm = new FormatToPattern(StringRes.getString("messaging.whoYouAre")).convert();
		info_whereIAm = new FormatToPattern(StringRes.getString("messaging.hereYouAre")).convert();
		info_discardedCard = new FormatToPattern(StringRes.getString("messaging.discardedCard")).convert();
		info_teamWinners = new FormatToPattern(StringRes.getString("messaging.winnerTeam")).convert();
		info_teamDefeated = new FormatToPattern(StringRes.getString("messaging.loserTeam")).convert();
		
		turn_askForObject = new FormatToPattern(StringRes.getString("messaging.askPlayObjectCard")).convert();
		turn_askForAttack = new FormatToPattern(StringRes.getString("messaging.askIfAttack")).convert();
		turn_askForNoisePos = new FormatToPattern(StringRes.getString("messaging.askForNoisePosition")).convert();
		turn_movement = new FormatToPattern(StringRes.getString("messaging.timeToMove")).convert();
		turn_askForLightsPos = new FormatToPattern(StringRes.getString("messaging.askForLightsPosition")).convert();
		turn_discard = new FormatToPattern(StringRes.getString("messaging.tooManyCardsAlien")).convert();
		turn_escaped = new FormatToPattern(StringRes.getString("messaging.EscapedSuccessfully")).convert();
		turn_failedEscape = new FormatToPattern(StringRes.getString("messaging.EscapeHatchDoesNotWork")).convert();
		
		event_ObjectUsed = new FormatToPattern(StringRes.getString("messaging.playerIsUsingObjCard")).convert();
		event_Noise = new FormatToPattern(StringRes.getString("messaging.noise")).convert();
		event_PlayerLocated = new FormatToPattern(StringRes.getString("messaging.disclosePlayerPosition")).convert();
		event_Attack = new FormatToPattern(StringRes.getString("messaging.playerAttacking")).convert();
		event_Death = new FormatToPattern(StringRes.getString("messaging.playerDied")).convert(); 
		event_EndGame = new FormatToPattern(StringRes.getString("messaging.endOfTheGame")).convert();
		event_Defense = new FormatToPattern(StringRes.getString("messaging.playerDefended")).convert();
		event_EndOfResults = new FormatToPattern(StringRes.getString("messaging.endOfResults")).convert();
		event_PlayerEscaped = new FormatToPattern(StringRes.getString("messaging.playerEscaped")).convert();
		
		//These ones are necessary so that we can display the JInputDialog (ex, for the position) again.
		//There will be a dialog that displays the exception message (whatever it is) and
		//the view will have to repeat the last dialog display...
		exception_1 = new FormatToPattern(StringRes.getString("messaging.exceptions.playerCanNotEnter")).convert();
		exception_2 = new FormatToPattern(StringRes.getString("messaging.exceptions.destinationUnreachable")).convert();
		exception_3 = new FormatToPattern(StringRes.getString("messaging.exceptions.wrongCard")).convert();
		
	}
	
	
	/**The method that is invoked whenever this class receives a notifyObserves(),
	 * because a new message has arrived to the Connection.
	 * It checks if the message corresponds to some given patterns, and then
	 * invokes the other 4 methods that check a specific kind of patterns
	 *(info, TurnRequests, events, exceptions) 
	 * @throws RemoteException */
	@Override
	protected void processMessage(String message) throws RemoteException {
		Matcher map = setGameMap.matcher(message);
		Matcher startmotd = getMOTDstart.matcher(message);
		Matcher gameStartETA = startInXSeconds.matcher(message);
		Matcher chatMsg = inboundChatMessage.matcher(message);	
		
		if (!handlingMOTDspecialCase(message)) {
			
			if (map.matches()) {
				String mapname = map.group(1);
				setMap(mapname);
				
			} else if (startmotd.matches()) {
				LOG.finer("Server has begun writing motd");
				readingMotd = true;
				
			} else if (chatMsg.matches()) {
				String author = chatMsg.group(1);
				String msg = chatMsg.group(2);
				visualizeChatMsg(author, msg);
				
			} else if (gameStartETA.matches()) {
				setStartETA(message);
				
			}
			processInfo(message);
			processTurnRequest(message);
			processEvent(message);
			processException(message);
			
		}  
		
	}
	
	private boolean processInfo(String message) throws RemoteException {
		Matcher currentTurnAndPlayer = info_currentTurnAndPlayer.matcher(message);
		Matcher lobbyPlr = info_numLobbyPlayers.matcher(message);
		Matcher playerRename = info_playerRenamed.matcher(message);
		Matcher myRename = info_whoIAm.matcher(message);
		Matcher myTeam = info_yourTeam.matcher(message);
		Matcher myPosition = info_whereIAm.matcher(message);
		Matcher drawncard = info_drawnObjectCard.matcher(message);
		Matcher discardedCard = info_discardedCard.matcher(message);
		
		Matcher playerDisconnected = info_playerDisconnected.matcher(message);
		Matcher playerConnected = info_playerConnected.matcher(message);
		
		Matcher winners = info_teamWinners.matcher(message);
		Matcher losers = info_teamDefeated.matcher(message);
		
		if (currentTurnAndPlayer.matches()) {
			int turnNumber = Integer.parseInt(currentTurnAndPlayer.group(1));
			String playerName = currentTurnAndPlayer.group(2); 
			startTurn(turnNumber, playerName);
			return true;
			
		} else if (playerRename.matches()) {
			String previousName = playerRename.group(1);
			String newName = playerRename.group(2);
			renamePlayer(previousName, newName);
			return true;
			
		} else if (lobbyPlr.matches()) {
			int current = Integer.parseInt(lobbyPlr.group(1));
			int max = Integer.parseInt(lobbyPlr.group(2));
			playersInLobby(current, max);
			return true;
			
		} else if (myRename.matches()) {
			String myNewName = myRename.group(1);
			renameMyself(myNewName);
			return true;
			
		} else if (myPosition.matches()) {
			String myPos = myPosition.group(1);
			setMyPosition(myPos);
			return true;
			
		} else if (myTeam.matches()) {
			String teamName = myTeam.group(1);
			setMyTeam(teamName);
			return true;
			
		} else if (drawncard.matches()) {
			String yyyCard =  drawncard.group(1);
			drawnCard(yyyCard);
			return true;
			
		} else if (playerDisconnected.matches()) {
			String playerName = playerDisconnected.group(1);
			playerDisconnected(playerName);
			return true;
			
		} else if (playerConnected.matches()) {
			int current = Integer.parseInt(playerConnected.group(1));
			int max = Integer.parseInt(playerConnected.group(2));
			playerConnected(current, max);
			return true;
			
		} else if (discardedCard.matches()) {
			String cardName = discardedCard.group(1);
			discardedCard(cardName);
			return true;
			
		} else if (winners.matches()) {
			String team = winners.group(1);
			String winnersNames = winners.group(2);
			setWinners(team, winnersNames);
			return true;
			
		} else if (losers.matches()) {
			String teamName = losers.group(1);
			setLoserTeam(teamName);
			return true;
		} 
		
		return false;
	}
	
	
	private boolean processTurnRequest(String message) throws RemoteException {
		
		Matcher turnStart = turn_Start.matcher(message);
		Matcher movement = turn_movement.matcher(message);
		Matcher askForAttack = turn_askForAttack.matcher(message);
		Matcher askForObject = turn_askForObject.matcher(message);
		Matcher whichobjectCard = input_ObjectCard.matcher(message);
		Matcher askForNoisePos = turn_askForNoisePos.matcher(message);
		Matcher turnEnd = turn_End.matcher(message);
		Matcher askForLightsPos = turn_askForLightsPos.matcher(message);
		Matcher discard = turn_discard.matcher(message);
		Matcher playOrDiscard = turn_playOrDiscard.matcher(message);
		Matcher escaped = turn_escaped.matcher(message);
		Matcher failedEscape = turn_failedEscape.matcher(message);
		
		if (turnEnd.matches()) {
			notMyTurn();
			return true;
			
		} else if (turnStart.matches()) {
			String myName = turnStart.group(1);
			String myPos = turnStart.group(2);
			startMyTurn(myName, myPos);
			return true;
			
		}   else if (movement.matches()) {
			askForMovement();
			return true;
			
		}  else if (askForAttack.matches()|| askForObject.matches()) {
			askForYesNo(message);
			return true;
			
		} else if (askForNoisePos.matches()) {
			askForNoisePosition();
			return true;
			
		} else if (askForLightsPos.matches()) {
			askForLightsPosition();
			return true;
			
		}else if (whichobjectCard.matches()) {
			whichObjectCard();
			return true;
			
		} else if (discard.matches()) {
			haveToDiscard();
			return true;
			
		} else if (playOrDiscard.matches()) {
			askPlayOrDiscard(message);
			return true;
			
		} else if (escaped.matches()) {
			youEscaped();
			return true;
			
		} else if (failedEscape.matches()) {
			failedEscape();
			return true;
		}
		return false;
	}


	

	private boolean processEvent (String message) throws RemoteException {
		
		Matcher eventNoise = event_Noise.matcher(message);
		Matcher eventObject = event_ObjectUsed.matcher(message);
		Matcher eventAttack = event_Attack.matcher(message);
		Matcher eventDeath = event_Death.matcher(message);
		Matcher eventEndGame = event_EndGame.matcher(message);
		Matcher eventFoundPlr = event_PlayerLocated.matcher(message);
		Matcher eventDefense = event_Defense.matcher(message);
		Matcher endResults = event_EndOfResults.matcher(message);
		Matcher eventPlayerEscaped = event_PlayerEscaped.matcher(message);
		
		if (eventObject.matches()) {
			String playerName = eventObject.group(1);
			String yyyCard = eventObject.group(2);
			eventObject(playerName, yyyCard, message);
			return true;
			
		} else if (eventAttack.matches()) {
			String attacker = eventAttack.group(1);
			String location = eventAttack.group(2);
			eventAttack(attacker, location);
			return true;
			
		} else if(eventNoise.matches()) {
			String location = eventNoise.group(1);
			eventNoise(location);
			return true;
			
		} else if (eventDeath.matches()) {
			String playerKilled = eventDeath.group(1);
			eventDeath(playerKilled);
			return true;
			
		} else if (eventEndGame.matches()) {
			eventEndGame();
			return true;
			
		} else if (endResults.matches()) {
			// this is the end of the end (LOL)
			// the final results dialog will be fired here
			endResults();
			return true;
			
		} else if (eventFoundPlr.matches()) {
			String playerName = eventFoundPlr.group(1);
			String location = eventFoundPlr.group(2);
			eventFoundPlayer(playerName, location);
			
			return true;
		} else if (eventDefense.matches()) {
			eventDefense(message);
		}
		 else if (eventPlayerEscaped.matches()) {
				String playerName = eventPlayerEscaped.group(1);
				eventPlayerEscaped(playerName);
			}
		
		return false;
	}
	
	private boolean processException(String message) throws RemoteException {
		
		Matcher moveCanNotEnter = exception_1.matcher(message);
		Matcher moveUnreachable = exception_2.matcher(message);
		Matcher wrongCard = exception_3.matcher(message);
		
		if (moveCanNotEnter.matches() || moveUnreachable.matches()) {
			showMovementException(message);
			return true;
		
		} else if (wrongCard.matches()) {
			showWrongCardException(message);

			return true;
		}
		return false;
	}
	
	
	private boolean isMe(String who) {
		return who.equals(model.getMyPlayerState().getMyName());
	}

	public String getCardGUIKey(String classname) {
		String ans = classname.substring(0, classname.length()-4);
		return ans.toLowerCase();
	}

	/**
	 * The Message Of The Day is a special situation, since it's a
	 * text body read in pieces
	 * @param message
	 * @return
	 */
	private boolean handlingMOTDspecialCase(String message) {
		Matcher endmotd = getMOTDend.matcher(message);
		if (readingMotd) {
			if (endmotd.matches()) {
				LOG.finer("Server has stopped writing motd");
				readingMotd = false;
				view.displayServerMOTD(loadedMotd);
			} else {
				loadedMotd = loadedMotd + "\n" + message;
			}
			return true;
		}
		return false;
	}
	
	//Here are methods to perform the commands / modify the model/ get the answers
	//Using the socket connection, these methods are invoked upon pattern recognition
	//Using the RMI connection, these methods are invoked directly through RMI
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#setMap(java.lang.String)
	 */
	@Override
	public void setMap(String mapname) {
		LOG.finer("Setting map to " +mapname);
		view.setGameMap(mapname);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#startReadingMotd()
	 */
	@Override
	public void setWholeMOTD(String text) throws RemoteException {
		LOG.finer("Server sent the motd");
		loadedMotd = text;
		view.displayServerMOTD(loadedMotd);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#visualizeChatMsg(java.lang.String, java.lang.String)
	 */
	@Override
	public void visualizeChatMsg(String author, String msg) throws RemoteException {
		view.newChatMessage(author, msg);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#setStartETA(java.lang.String)
	 */
	@Override
	public void setStartETA(String message) throws RemoteException {
		LOG.finer("Setting game start ETA");
		model.setGameStatus(GameStatus.GOING_TO_START);
		model.finishedUpdating();
		view.setTurnStatusString(message);
	}
	
	
	//da processInfo(message)
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#startTurn(int, java.lang.String)
	 */
	@Override
	public void startTurn(int turnNumber, String playerName) throws RemoteException {
		LOG.finer("Someone's turn");
		view.setTurnStatusString(playerName + " is playing");
		model.updateNowPlaying(playerName);
		model.updatePlayerStatus(model.getNowPlaying().getMyName(), CurrentPlayerStatus.ALIVE);
		model.setGameStatus(GameStatus.RUNNING);
		model.setTurnNumber(turnNumber);
		model.finishedUpdating();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#renamePlayer(java.lang.String, java.lang.String)
	 */
	@Override
	public void renamePlayer(String previousName, String changedName) throws RemoteException {
		LOG.finer("Someone renamed himself");
		model.updatePlayerRename(previousName, changedName);
		model.finishedUpdating();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#renameMyself(java.lang.String)
	 */
	@Override
	public void renameMyself(String myNewName) throws RemoteException {
		LOG.finer("Read player name from server: " +myNewName);
		model.getMyPlayerState().setMyName(myNewName);
		model.finishedUpdating();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#setMyPosition(java.lang.String)
	 */
	@Override
	public void setMyPosition(String myPos) throws RemoteException {
		LOG.finer("Read player position from server: [" + myPos + "]");
		model.getMyPlayerState().setLocation(myPos);
		model.finishedUpdating();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#setMyTeam(java.lang.String)
	 */
	@Override
	public void setMyTeam(String teamName) throws RemoteException {
		LOG.finer("Read team name from server");
		model.getMyPlayerState().setMyTeam(teamName);
		model.finishedUpdating();
		view.discoverMyName();  // if someone else's playing we don't know it yet
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#drawnCard(java.lang.String)
	 */
	@Override
	public void drawnCard(String cardClassName) throws RemoteException {
		LOG.finer("Server reported new object card " + cardClassName);
		String cardKey = getCardGUIKey(cardClassName);
		model.getMyPlayerState().addCard(cardKey);
		model.finishedUpdating();
		view.notifyNewCard(cardKey);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#discardedCard(java.lang.String)
	 */
	@Override
	public void discardedCard(String cardName) throws RemoteException {
		model.getMyPlayerState().removeCard(cardName);
		model.finishedUpdating();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#playerDisconnected(java.lang.String)
	 */
	@Override
	public void playerDisconnected(String playerName) throws RemoteException {
		model.getSpecificPlayerState(playerName).setMyStatus(CurrentPlayerStatus.DISCONNECTED);
		model.finishedUpdating();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#setWinners(java.lang.String, java.lang.String)
	 */
	@Override
	public void setWinners(String team, String winnersNames) throws RemoteException {
		LOG.finer("Server listed the winners");
		model.getVictoryState().addWinners(team, winnersNames);
		model.finalRefreshPlayerStatus();
		model.finishedUpdating();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#setLoserTeam(java.lang.String)
	 */
	@Override
	public void setLoserTeam(String teamName) throws RemoteException {
		LOG.finer("Server listed the losers");
		model.getVictoryState().setTeamDefeated(teamName);
		model.finalRefreshPlayerStatus();
		model.finishedUpdating();
	}
	
	//da processTurnRequest(msg)
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#notMyTurn()
	 */
	@Override
	public void notMyTurn() throws RemoteException {
		view.clearNoisesFromMap();
		view.setTurnStatusString("waiting for my turn");
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#startMyTurn(java.lang.String, java.lang.String)
	 */
	@Override
	public void startMyTurn(String myName, String myPos) throws RemoteException {
		LOG.finer("My turn");
		view.setTurnStatusString("now is my turn to play");
		model.getMyPlayerState().setMyName(myName);
		model.getMyPlayerState().setLocation(myPos);
		model.getMyPlayerState().setMyStatus(CurrentPlayerStatus.ALIVE);
		model.finishedUpdating();
		view.clearOtherPlayersFromMap();
		view.focusOnLocation(model.getMyPlayerState().getLocation(), 2000);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#askForMovement()
	 */
	@Override
	public void askForMovement() throws RemoteException {
		LOG.finer("Server asked to move");
		view.notifyUser("Please move your character: click where you want to go");
		view.bindPositionSender();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#askForYesNo(java.lang.String)
	 */
	@Override
	public void askForYesNo(String question) throws RemoteException {
		LOG.finer("Server asked yes/no question");
		view.relayYesNoDialog(question);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#askForNoisePosition()
	 */
	@Override
	public void askForNoisePosition() throws RemoteException {
		LOG.finer("Server asked to place a noise");
		view.notifyUser("Select the sector you want to make a noise in");
		view.bindPositionSender();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#askForLightsPosition()
	 */
	@Override
	public void askForLightsPosition() throws RemoteException {
		LOG.finer("Server asked where to turn the Lights on");
		view.notifyUser("Select the sector where you want to turn the lights on");
		view.bindPositionSender();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#whichObjectCard()
	 */
	@Override
	public void whichObjectCard() throws RemoteException {
		LOG.finer("Server asked an object card");
		view.relayObjectCard();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#haveToDiscard()
	 */
	@Override
	public void haveToDiscard() throws RemoteException {
		LOG.finer("Server asked to discard a card");
		view.relayObjectCard();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#askPlayOrDiscard(java.lang.String)
	 */
	@Override
	public void askPlayOrDiscard(String question) throws RemoteException {
		LOG.finer("Server asked to play or discard a card");
		view.relayYesNoDialog(question, "play", "discard");
	}
	
	
	//da processEvent(msg)
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#eventObject(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void eventObject(String playerName, String cardClassName, String message) throws RemoteException {
		if (!isMe(playerName)) { // it's not me
			view.notifyUser(message);
		}
		if(isMe(playerName)) { 	//if it's me
			String cardName = getCardGUIKey(cardClassName);
			model.getMyPlayerState().removeCard(cardName);	//remove the card from my hand in the Client
			model.finishedUpdating();
		}
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#eventAttack(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void eventAttack(String attacker, String location) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.playerAttacking"),
				attacker,
				location);
		if (!isMe(attacker)) { // it's not me
			view.notifyUser(message);
		}
		model.getNowPlaying().setLastNoiseLocation(location);
		model.finishedUpdating();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#eventNoise(java.lang.String)
	 */
	@Override
	public void eventNoise(String location) throws RemoteException {
		model.getNowPlaying().setLastNoiseLocation(location);
		model.finishedUpdating();
		view.addNoiseToMap(location);
		view.focusOnLocation(location, 0);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#eventDeath(java.lang.String, java.lang.String)
	 */
	@Override
	public void eventDeath(String playerKilled) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.playerDied"),
				playerKilled);
		model.getSpecificPlayerState(playerKilled).setMyStatus(CurrentPlayerStatus.DEAD);
		if (isMe(playerKilled))
			model.getMyPlayerState().setMyStatus(CurrentPlayerStatus.DEAD);
		model.finishedUpdating();
		view.notifyUser(message);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#eventEndGame()
	 */
	@Override
	public void eventEndGame() throws RemoteException {
		model.setGameStatus(GameStatus.FINISHED);
		model.finishedUpdating();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#endResults()
	 */
	@Override
	public void endResults() throws RemoteException {
		LOG.finer("Server sent results, printing recap screen");
		view.spawnVictoryRecap(model);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#eventFoundPlayer(java.lang.String, java.lang.String)
	 */
	@Override
	public void eventFoundPlayer(String playerName, String location) throws RemoteException {
		if (!isMe(playerName)) { // it's not me
			view.addOtherPlayerToMap(location, playerName);
			view.focusOnLocation(location, 0);
			model.getSpecificPlayerState(playerName).setLastNoiseLocation(location);
			model.finishedUpdating();
		}		
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#eventDefense(java.lang.String)
	 */
	@Override
	public void eventDefense(String location) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.playerDefended"),
				location);
		view.notifyUser(message);
	}
	
	public void eventPlayerEscaped(String playerName) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.playerEscaped"),
				playerName,
				StringRes.getString("ship_name"));
		model.getSpecificPlayerState(playerName).setMyStatus(CurrentPlayerStatus.WINNER);
		model.finishedUpdating();
		if (!isMe(playerName)) {
			view.notifyUser(message);
		}
		
	}
	
	//da processException(msg) n:Not working properly right now
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#showMovementException(java.lang.String)
	 */
	@Override
	public void showMovementException(String exceptionMessage) throws RemoteException { 
		LOG.finer("Server reported : movement is impossible." );
		view.notifyUser(exceptionMessage);
		askForMovement();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.client.controller.gui.ClientProceduresInterface#showWrongCardException(java.lang.String)
	 */
	@Override
	public void showWrongCardException(String exceptionMessage) throws RemoteException {
		LOG.finer("Server reported : that Card can't be played now." );
		view.notifyUser(exceptionMessage);
		askForYesNo(StringRes.getString("messaging.askPlayObjectCard"));
	}

	@Override
	public void playerConnected(int current, int maximum) throws RemoteException {
		// TODO show something
	}

	@Override
	public void playersInLobby(int current, int maximum) throws RemoteException {
		// TODO show something
	}
	
	public void failedEscape() throws RemoteException {
		// TODO show something
	}

	@Override
	public void youEscaped() {
		model.getMyPlayerState().setMyStatus(CurrentPlayerStatus.WINNER);
		model.finishedUpdating();
	}
	
}
