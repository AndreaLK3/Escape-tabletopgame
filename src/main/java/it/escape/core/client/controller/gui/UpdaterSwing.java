package it.escape.core.client.controller.gui;

import it.escape.core.client.BindUpdaterInterface;
import it.escape.core.client.controller.Updater;
import it.escape.core.client.model.CurrentPlayerStatus;
import it.escape.core.client.model.GameStatus;
import it.escape.core.client.model.ModelForGUI;
import it.escape.core.client.model.PlayerState;
import it.escape.core.server.model.game.exceptions.DestinationUnreachableException;
import it.escape.core.server.model.game.exceptions.PlayerCanNotEnterException;
import it.escape.core.server.model.game.exceptions.WrongCardException;
import it.escape.tools.MessageCarrier;
import it.escape.tools.strings.FormatToPattern;
import it.escape.tools.strings.StringRes;
import it.escape.tools.utils.LogHelper; 

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
	
	private static final Logger LOGGER = Logger.getLogger( UpdaterSwing.class.getName() );

	private UpdaterSwingToViewInterface view;
	
	private boolean readingMotd = false;
	
	private ModelForGUI model;
	
	private String loadedMotd = "";
	
	private Pattern pINFOnumLobbyPlayers;
	private Pattern pINFOplayerConnected;
	private Pattern pINFOplayerDisconnected;
	private Pattern pINFOyourTeam;
	private Pattern pINFOcurrentTurnAndPlayer;
	private Pattern pINFOplayerRenamed;
	private Pattern pINFOdrawnObjectCard;
	private Pattern pINFOwhoIAm;
	private Pattern pINFOwhereIAm;
	private Pattern pINFOdiscardedCard;
	private Pattern pINFOteamWinners;
	private Pattern pINFOteamDefeated;
	private Pattern pINFOmyDefense;
	
	private Pattern pTURNaskForAttack;
	private Pattern pTURNaskForNoisePos;
	private Pattern pTURNmovement;
	private Pattern pTURNaskForObject;
	private Pattern pTURNaskForLightsPos;
	private Pattern pTURNdiscard;
	private Pattern pTURNescaped;
	private Pattern pTURNfailedEscape;
	
	private Pattern pEVENTnoise;
	private Pattern pEVENTobjectUsed;
	private Pattern pEVENTplayerLocated;
	private Pattern pEVENTattack;
	private Pattern pEVENTdeath;
	private Pattern pEVENTdefense;
	private Pattern pEVENTendGame;
	private Pattern pEVENTendOfResults;
	
	private Pattern pEVENTPlayerEscaped;
	private Pattern pEVENTPodUnavailable;
	
	private Pattern pEXCEPTIONcanNotEnter;
	private Pattern pEXCEPTIONdestUnreachable;
	private Pattern pEXCEPTIONwrongCard;

	
	/**The constructor*/
	public UpdaterSwing(ModelForGUI model) {
		super();
		LogHelper.setDefaultOptions(LOGGER);
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
				LOGGER.severe("RemoteException was thrown while RMI was NOT supposed to be running: " + e.getMessage());
			}
		}
	}
	
	
	protected void initPatterns() {
		super.initPatterns();
		
		pINFOnumLobbyPlayers = new FormatToPattern(StringRes.getString("messaging.othersWaiting")).convert();
		pINFOplayerConnected = new FormatToPattern(StringRes.getString("messaging.playerConnected")).convert();
		pINFOplayerDisconnected =  new FormatToPattern(StringRes.getString("messaging.playerDisconnected")).convert();
		pINFOyourTeam = new FormatToPattern(StringRes.getString("messaging.gamemaster.playAs")).convert();
		pINFOcurrentTurnAndPlayer = new FormatToPattern(StringRes.getString("messaging.timecontroller.turnNumber")).convert();
		pINFOdrawnObjectCard = new FormatToPattern(StringRes.getString("messaging.objectCardDrawn")).convert();
		pINFOplayerRenamed  = new FormatToPattern(StringRes.getString("messaging.announceRename")).convert();
		pINFOwhoIAm = new FormatToPattern(StringRes.getString("messaging.whoYouAre")).convert();
		pINFOwhereIAm = new FormatToPattern(StringRes.getString("messaging.hereYouAre")).convert();
		pINFOdiscardedCard = new FormatToPattern(StringRes.getString("messaging.discardedCard")).convert();
		pINFOteamWinners = new FormatToPattern(StringRes.getString("messaging.winnerTeam")).convert();
		pINFOteamDefeated = new FormatToPattern(StringRes.getString("messaging.loserTeam")).convert();
		pINFOmyDefense = new FormatToPattern(StringRes.getString("messaging.defendedSuccessfully")).convert();
		
		pTURNaskForObject = new FormatToPattern(StringRes.getString("messaging.askPlayObjectCard")).convert();
		pTURNaskForAttack = new FormatToPattern(StringRes.getString("messaging.askIfAttack")).convert();
		pTURNaskForNoisePos = new FormatToPattern(StringRes.getString("messaging.askForNoisePosition")).convert();
		pTURNmovement = new FormatToPattern(StringRes.getString("messaging.timeToMove")).convert();
		pTURNaskForLightsPos = new FormatToPattern(StringRes.getString("messaging.askForLightsPosition")).convert();
		pTURNdiscard = new FormatToPattern(StringRes.getString("messaging.tooManyCardsAlien")).convert();
		pTURNescaped = new FormatToPattern(StringRes.getString("messaging.EscapedSuccessfully")).convert();
		pTURNfailedEscape = new FormatToPattern(StringRes.getString("messaging.EscapeHatchDoesNotWork")).convert();
		pEVENTPodUnavailable = new FormatToPattern(StringRes.getString("messaging.EscapeHatchDisabled")).convert();
		
		pEVENTobjectUsed = new FormatToPattern(StringRes.getString("messaging.playerIsUsingObjCard")).convert();
		pEVENTnoise = new FormatToPattern(StringRes.getString("messaging.noise")).convert();
		pEVENTplayerLocated = new FormatToPattern(StringRes.getString("messaging.disclosePlayerPosition")).convert();
		pEVENTattack = new FormatToPattern(StringRes.getString("messaging.playerAttacking")).convert();
		pEVENTdeath = new FormatToPattern(StringRes.getString("messaging.playerDied")).convert(); 
		pEVENTendGame = new FormatToPattern(StringRes.getString("messaging.endOfTheGame")).convert();
		pEVENTdefense = new FormatToPattern(StringRes.getString("messaging.playerDefended")).convert();
		pEVENTendOfResults = new FormatToPattern(StringRes.getString("messaging.endOfResults")).convert();
		pEVENTPlayerEscaped = new FormatToPattern(StringRes.getString("messaging.playerEscaped")).convert();
		
		//These ones are necessary so that we can display the JInputDialog (ex, for the position) again.
		//There is a dialog that displays the exception message (whatever it is) and
		//then the view will have to repeat the last dialog display.
		Exception exception1 = new PlayerCanNotEnterException();
		Exception exception2 = new DestinationUnreachableException();
		Exception exception3 = new WrongCardException();
		pEXCEPTIONcanNotEnter = new FormatToPattern(exception1.getClass().getSimpleName() + " : " + StringRes.getString("messaging.exceptions.playerCanNotEnter")).convert();
		pEXCEPTIONdestUnreachable = new FormatToPattern(exception2.getClass().getSimpleName() + " : " + StringRes.getString("messaging.exceptions.destinationUnreachable")).convert();
		pEXCEPTIONwrongCard = new FormatToPattern(exception3.getClass().getSimpleName() + " : " + StringRes.getString("messaging.exceptions.wrongCard")).convert();
		
	}
	
	
	/**The method that is invoked whenever this class receives a notifyObserves(),
	 * because a new message has arrived to the Connection.
	 * It checks if the message corresponds to some given patterns, and then
	 * invokes the other 4 methods that check a specific kind of patterns
	 *(info, TurnRequests, events, exceptions) 
	 * @throws RemoteException */
	@Override
	public void processMessage(String message) throws RemoteException {
		Matcher map = setGameMap.matcher(message);
		Matcher startmotd = getMOTDstart.matcher(message);
		Matcher gameStartETA = startInXSeconds.matcher(message);
		Matcher chatMsg = inboundChatMessage.matcher(message);	
		
		if (!handlingMOTDspecialCase(message)) {
			
			if (map.matches()) {
				String mapname = map.group(1);
				setMap(mapname);
				
			} else if (startmotd.matches()) {
				LOGGER.finer("Server has begun writing motd");
				readingMotd = true;
				
			} else if (chatMsg.matches()) {
				String author = chatMsg.group(1);
				String msg = chatMsg.group(2);
				visualizeChatMsg(author, msg);
				
			} else if (gameStartETA.matches()) {
				int time = Integer.parseInt(gameStartETA.group(1));
				setStartETA(time);
				
			}
			processInfo(message);
			processTurnRequest(message);
			processEvent(message);
			processException(message);
			
		}  
		
	}
	
	/**This method is invoked by processMessage(String message)*/
	private boolean processInfo(String message) throws RemoteException {
		boolean messageMatched = false;
		
		messageMatched = processInfoAboutMe(message);
		messageMatched = processInfoAboutTheGame(message);
		
		return messageMatched;
	}
	
	private boolean processInfoAboutMe (String message) throws RemoteException  {
		Matcher myRename = pINFOwhoIAm.matcher(message);
		Matcher myTeam = pINFOyourTeam.matcher(message);
		Matcher myPosition = pINFOwhereIAm.matcher(message);
		Matcher drawncard = pINFOdrawnObjectCard.matcher(message);
		Matcher discardedCard = pINFOdiscardedCard.matcher(message);
		Matcher myDefense = pINFOmyDefense.matcher(message);
		
		
		if (myRename.matches()) {
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
			
		} else if (discardedCard.matches()) {
			String cardName = discardedCard.group(1);
			discardedCard(cardName);
			return true;
			
		} else if (myDefense.matches()) {
			youDefended();
			return true;
		} 
		
		return false;
	}
	

	private boolean processInfoAboutTheGame(String message) throws RemoteException {
		Matcher currentTurnAndPlayer = pINFOcurrentTurnAndPlayer.matcher(message);
		Matcher lobbyPlr = pINFOnumLobbyPlayers.matcher(message);
		Matcher playerRename = pINFOplayerRenamed.matcher(message);
		Matcher playerDisconnected = pINFOplayerDisconnected.matcher(message);
		Matcher playerConnected = pINFOplayerConnected.matcher(message);
		Matcher winners = pINFOteamWinners.matcher(message);
		Matcher losers = pINFOteamDefeated.matcher(message);
		
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
			
		} else if (playerDisconnected.matches()) {
			String playerName = playerDisconnected.group(1);
			playerDisconnected(playerName);
			return true;
			
		} else if (playerConnected.matches()) {
			int current = Integer.parseInt(playerConnected.group(1));
			int max = Integer.parseInt(playerConnected.group(2));
			playerConnected(current, max);
			return true;
			
		}  else if (winners.matches()) {
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
	
	/**This method is invoked by processMessage(String message)*/
	private boolean processTurnRequest(String message) throws RemoteException {
		
		Matcher turnStart = turn_Start.matcher(message);
		Matcher movement = pTURNmovement.matcher(message);
		Matcher askForAttack = pTURNaskForAttack.matcher(message);
		Matcher askForObject = pTURNaskForObject.matcher(message);
		Matcher whichobjectCard = input_ObjectCard.matcher(message);
		Matcher askForNoisePos = pTURNaskForNoisePos.matcher(message);
		Matcher turnEnd = turn_End.matcher(message);
		Matcher askForLightsPos = pTURNaskForLightsPos.matcher(message);
		Matcher discard = pTURNdiscard.matcher(message);
		Matcher playOrDiscard = turn_playOrDiscard.matcher(message);
		Matcher escaped = pTURNescaped.matcher(message);
		Matcher failedEscape = pTURNfailedEscape.matcher(message);
		
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
			askWhichObjectCard();
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


	
	/**This method is invoked by processMessage(String message)*/
	private boolean processEvent (String message) throws RemoteException {
		
		Matcher eventNoise = pEVENTnoise.matcher(message);
		Matcher eventObject = pEVENTobjectUsed.matcher(message);
		Matcher eventAttack = pEVENTattack.matcher(message);
		Matcher eventDeath = pEVENTdeath.matcher(message);
		Matcher eventEndGame = pEVENTendGame.matcher(message);
		Matcher eventFoundPlr = pEVENTplayerLocated.matcher(message);
		Matcher eventDefense = pEVENTdefense.matcher(message);
		Matcher endResults = pEVENTendOfResults.matcher(message);
		Matcher eventPlayerEscaped = pEVENTPlayerEscaped.matcher(message);
		Matcher eventHatchDisabled = pEVENTPodUnavailable.matcher(message);
		
		if (eventObject.matches()) {
			String playerName = eventObject.group(1);
			String yyyCard = eventObject.group(2);
			eventObject(playerName, yyyCard);
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
			String location = eventDefense.group(1);
			eventDefense(location);
			return true;
			
		} else if (eventPlayerEscaped.matches()) {
			String playerName = eventPlayerEscaped.group(1);
			eventPlayerEscaped(playerName);
			return true;
			
		} else if (eventHatchDisabled.matches()) {
			String location = eventHatchDisabled.group(1);
			eventEscapePodUnavailable(location);
			return true;
		}
		
		return false;
	}
	
	/**This method is invoked by processMessage(String message)*/
	private boolean processException(String message) throws RemoteException {
		
		Matcher moveCanNotEnter = pEXCEPTIONcanNotEnter.matcher(message);
		Matcher moveUnreachable = pEXCEPTIONdestUnreachable.matcher(message);
		Matcher wrongCard = pEXCEPTIONwrongCard.matcher(message);
		
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
				LOGGER.finer("Server has stopped writing motd");
				readingMotd = false;
				view.displayServerMOTD(loadedMotd);
			} else {
				if (loadedMotd != "") {
					loadedMotd = loadedMotd + "\n";
				}
				loadedMotd = loadedMotd + message;
			}
			return true;
		}
		return false;
	}
	
	//Here are methods to perform the commands / modify the model/ get the answers
	//Using the socket connection, these methods are invoked upon pattern recognition
	//Using the RMI connection, these methods are invoked directly through RMI
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#setMap(java.lang.String) */
	@Override
	public void setMap(String mapname) {
		LOGGER.finer("Setting map to " +mapname);
		view.setGameMap(mapname);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#startReadingMotd() */
	@Override
	public void setWholeMOTD(String text) throws RemoteException {
		LOGGER.finer("Server sent the motd");
		loadedMotd = text;
		view.displayServerMOTD(loadedMotd);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#visualizeChatMsg(java.lang.String, java.lang.String) */
	@Override
	public void visualizeChatMsg(String author, String msg) throws RemoteException {
		view.newChatMessage(author, msg);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#setStartETA(java.lang.String) */
	@Override
	public void setStartETA(int seconds) throws RemoteException {
		LOGGER.finer("Setting game start ETA");
		model.setGameStatus(GameStatus.GOING_TO_START);
		model.getMyPlayerState().setMyStatus(CurrentPlayerStatus.CONNECTED);
		model.finishedUpdating();
		view.setTurnStatusTimer(StringRes.getString("messaging.gameStartETA"), seconds);
	}
	
	
	//da processInfo(message)
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#startTurn(int, java.lang.String) */
	@Override
	public void startTurn(int turnNumber, String playerName) throws RemoteException {
		LOGGER.finer("Someone's turn");
		view.setTurnStatusString(playerName + " is playing");
		model.updateNowPlaying(playerName);
		model.updatePlayerStatus(model.getNowPlaying().getMyName(), CurrentPlayerStatus.ALIVE);
		model.setGameStatus(GameStatus.RUNNING);
		model.setTurnNumber(turnNumber);
		model.finishedUpdating();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#renamePlayer(java.lang.String, java.lang.String) */
	@Override
	public void renamePlayer(String previousName, String changedName) throws RemoteException {
		LOGGER.finer("Someone renamed himself");
		model.updatePlayerRename(previousName, changedName);
		model.finishedUpdating();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#renameMyself(java.lang.String) */
	@Override
	public void renameMyself(String myNewName) throws RemoteException {
		LOGGER.finer("Read player name from server: " +myNewName);
		model.getMyPlayerState().setMyName(myNewName);
		if (model.getMyPlayerState().getMyStatus() == CurrentPlayerStatus.DISCONNECTED) {
			model.getMyPlayerState().setMyStatus(CurrentPlayerStatus.CONNECTED);
		}
		model.finishedUpdating();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#setMyPosition(java.lang.String) */
	@Override
	public void setMyPosition(String myPos) throws RemoteException {
		LOGGER.finer("Read player position from server: [" + myPos + "]");
		model.getMyPlayerState().setLocation(myPos);
		model.finishedUpdating();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#setMyTeam(java.lang.String) */
	@Override
	public void setMyTeam(String teamName) throws RemoteException {
		LOGGER.finer("Read team name from server");
		model.getMyPlayerState().setMyTeam(teamName);
		model.finishedUpdating();
		view.discoverMyName();  // if someone else's playing we don't know it yet
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#drawnCard(java.lang.String) */
	@Override
	public void drawnCard(String cardClassName) throws RemoteException {
		LOGGER.finer("Server reported new object card " + cardClassName);
		String cardKey = getCardGUIKey(cardClassName);
		model.getMyPlayerState().addCard(cardKey);
		model.finishedUpdating();
		view.notifyNewCard(cardKey);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#discardedCard(java.lang.String) */
	@Override
	public void discardedCard(String cardName) throws RemoteException {
		model.getMyPlayerState().removeCard(cardName);
		LOGGER.finer("I (should) have removed the " + cardName + "card from my hand of cards the model");
		model.finishedUpdating();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#playerDisconnected(java.lang.String) */
	@Override
	public void playerDisconnected(String playerName) throws RemoteException {
		PlayerState playerState = model.getSpecificPlayerState(playerName);
		if (playerState!=null) {
			playerState.setMyStatus(CurrentPlayerStatus.DISCONNECTED);
		}
		model.aPlayerDisconnected();
		model.finishedUpdating();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#setWinners(java.lang.String, java.lang.String)
	 */
	@Override
	public void setWinners(String team, String winnersNames) throws RemoteException {
		LOGGER.finer("Server listed the winners");
		model.getVictoryState().addWinners(team, winnersNames);
		model.finalRefreshPlayerStatus();
		model.finishedUpdating();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#setLoserTeam(java.lang.String)
	 */
	@Override
	public void setLoserTeam(String teamName) throws RemoteException {
		LOGGER.finer("Server listed the losers");
		model.getVictoryState().setTeamDefeated(teamName);
		model.finalRefreshPlayerStatus();
		model.finishedUpdating();
	}
	
	@Override
	public void playerConnected(int current, int maximum) throws RemoteException {
		updatePlayersConnected(current, maximum);
	}

	@Override
	public void playersInLobby(int current, int maximum) throws RemoteException {
		updatePlayersConnected(current, maximum);
	}
	
	/**Updates the variables in the model that hold the number of players currently connected, and
	 * the maximum number of players allowed*/
	private void updatePlayersConnected(int current, int maximum){
		model.getMyPlayerState().setMyStatus(CurrentPlayerStatus.CONNECTED);
		model.setPlayersConnected(current);
		model.setMaxPlayers(maximum);
		model.finishedUpdating();
	}
	
	//da processTurnRequest(msg)
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#notMyTurn()
	 */
	@Override
	public void notMyTurn() throws RemoteException {
		view.clearOtherPlayersFromMap();
		view.clearBonesFromMap();
		view.clearNoisesFromMap();
		view.clearAttacksFromMap();
		view.setTurnStatusString("waiting for my turn");
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#startMyTurn(java.lang.String, java.lang.String)
	 */
	@Override
	public void startMyTurn(String myName, String myPos) throws RemoteException {
		LOGGER.finer("My turn");
		view.setTurnStatusString("now is my turn to play");
		model.getMyPlayerState().setMyName(myName);
		model.getMyPlayerState().setLocation(myPos);
		model.getMyPlayerState().setMyStatus(CurrentPlayerStatus.ALIVE);
		model.finishedUpdating();
		view.focusOnLocation(model.getMyPlayerState().getLocation(), 2000);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#askForMovement() */
	@Override
	public void askForMovement() throws RemoteException {
		LOGGER.finer("Server asked to move");
		view.notifyUser("Please move your character: click where you want to go");
		view.bindPositionSender();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#askForYesNo(java.lang.String) */
	@Override
	public void askForYesNo(String question) throws RemoteException {
		LOGGER.finer("Server asked yes/no question");
		view.relayYesNoDialog(question);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#askForNoisePosition() */
	@Override
	public void askForNoisePosition() throws RemoteException {
		LOGGER.finer("Server asked to place a noise");
		view.notifyUser("Select the sector you want to make a noise in");
		view.bindPositionSender();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#askForLightsPosition() */
	@Override
	public void askForLightsPosition() throws RemoteException {
		LOGGER.finer("Server asked where to turn the Lights on");
		view.notifyUser("Select the sector where you want to turn the lights on");
		view.bindPositionSender();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#whichObjectCard() */
	@Override
	public void askWhichObjectCard() throws RemoteException {
		LOGGER.finer("Server asked an object card");
		view.relayObjectCard();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#haveToDiscard() */
	@Override
	public void haveToDiscard() throws RemoteException {
		LOGGER.finer("Server asked to discard a card");
		view.relayObjectCard();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#askPlayOrDiscard(java.lang.String) */
	@Override
	public void askPlayOrDiscard(String question) throws RemoteException {
		LOGGER.finer("Server asked to play or discard a card");
		view.relayYesNoDialog(question, "play", "discard");
	}
	
	
	//da processEvent(msg)
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#eventObject(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void eventObject(String playerName, String cardClassName) throws RemoteException {
		if (!isMe(playerName)) { // it's not me
			String newMsg = String.format(StringRes.getString("messaging.playerIsUsingObjCard"),
					playerName,cardClassName,playerName);
			view.notifyUser(newMsg);
		}
		if(isMe(playerName)) { 	//if it's me
			String cardName = getCardGUIKey(cardClassName);
			model.getMyPlayerState().removeCard(cardName);	//remove the card from my hand in the Client
			LOGGER.finer("I (should) have removed the " + cardName + "card from my hand of cards the model");
			model.finishedUpdating();
		}
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#eventAttack(java.lang.String, java.lang.String, java.lang.String) */
	@Override
	public void eventAttack(String attacker, String location) throws RemoteException {
		if (!isMe(attacker)) {
			view.addAttackToMap(location);
			view.focusOnLocation(location, 0);
		}
		if (isMe(attacker) && "humans".equalsIgnoreCase(model.getMyPlayerState().getMyTeam())) {
			model.getMyPlayerState().removeCard("attack");
		}
		model.getNowPlaying().setLastNoiseLocation(location);
		model.finishedUpdating();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#eventNoise(java.lang.String) */
	@Override
	public void eventNoise(String location) throws RemoteException {
		model.getNowPlaying().setLastNoiseLocation(location);
		model.finishedUpdating();
		view.addNoiseToMap(location);
		view.focusOnLocation(location, 0);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#eventDeath(java.lang.String, java.lang.String) */
	@Override
	public void eventDeath(String playerKilled) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.playerDied"),	playerKilled);
		model.getSpecificPlayerState(playerKilled).setMyStatus(CurrentPlayerStatus.DEAD);
		if (isMe(playerKilled)) {
			model.getMyPlayerState().setMyStatus(CurrentPlayerStatus.DEAD);
		}
		model.finishedUpdating();
		view.notifyUser(message);
		// the model is smart enough to display the bones in the right position
		view.addBonesToMap(model.getNowPlaying().getLastNoiseLocation());
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#eventEndGame() */
	@Override
	public void eventEndGame() throws RemoteException {
		model.setGameStatus(GameStatus.FINISHED);
		model.finishedUpdating();
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#endResults() */
	@Override
	public void endResults() throws RemoteException {
		LOGGER.finer("Server sent results, printing recap screen");
		view.spawnVictoryRecap(model);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#eventFoundPlayer(java.lang.String, java.lang.String) */
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
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#eventDefense(java.lang.String) */
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
	
	//da processException(msg)
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#showMovementException(java.lang.String) */
	@Override
	public void showMovementException(String exceptionMessage) throws RemoteException { 
		LOGGER.finer("Server reported : movement is impossible." );
		view.notifyUser(exceptionMessage);
		//Since the ServerSocketCore sends the String "You have to move your character on the map" again,
		//it is not necessary to invoke askForMovement
	}
	
	/* (non-Javadoc)
	 * @see it.escape.core.client.controller.gui.ClientProceduresInterface#showWrongCardException(java.lang.String) */
	@Override
	public void showWrongCardException(String exceptionMessage) throws RemoteException {
		LOGGER.finer("Server reported : that Card can't be played now." );
		view.notifyUser(exceptionMessage);
		//Since the ServerSocketCore sends the String with the question "Do you want to play an Object Card?" again,
		//it is not necessary to invoke askForObjectCard
	}

	
	public void failedEscape() throws RemoteException {
		String message = StringRes.getString("messaging.EscapeHatchDoesNotWork");
		view.notifyUser(message);
	}

	@Override
	public void youEscaped() {
		model.getMyPlayerState().setMyStatus(CurrentPlayerStatus.WINNER);
		model.finishedUpdating();
		String message = StringRes.getString("messaging.EscapedSuccessfully");
		view.notifyUser(message);
	}
	
	@Override
	public void youDefended() {
		String message = StringRes.getString("messaging.defendedSuccessfully");
		view.notifyUser(message);
		model.getMyPlayerState().removeCard("defense");
		model.finishedUpdating();
	}

	@Override
	public void eventEscapePodUnavailable(String location)
			throws RemoteException {
		LOGGER.finer("Server reported : escape pod " + location + " is disabled." );
		view.addClosedHatch(location);
		view.focusOnLocation(location, 0);
	}
	
}
