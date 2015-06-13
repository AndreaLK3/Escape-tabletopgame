package it.escape.client.controller.gui;

import it.escape.client.controller.MessageCarrier;
import it.escape.client.controller.Updater;
import it.escape.client.model.CurrentPlayerStatus;
import it.escape.client.model.GameStatus;
import it.escape.client.model.ModelForGUI;
import it.escape.client.view.gui.BindUpdaterInterface;
import it.escape.strings.FormatToPattern;
import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Updates the GUIModel and/or sends commands to the GUIView when a new network message arrives
 * @author michele, andrea
 */
public class UpdaterSwing extends Updater implements Observer, BindUpdaterInterface {
	
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
	private Pattern turn_playOrDiscard;
	
	private Pattern event_Noise;
	private Pattern event_ObjectUsed;
	private Pattern event_PlayerLocated;
	private Pattern event_Attack;
	private Pattern event_Death;
	private Pattern event_Defense;
	private Pattern event_EndGame;
	private Pattern event_EndOfResults;
	
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
			processMessage(msg.getMessage());
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
		turn_playOrDiscard =  new FormatToPattern(StringRes.getString("messaging.tooManyCardsHuman")).convert();
		
		event_ObjectUsed = new FormatToPattern(StringRes.getString("messaging.playerIsUsingObjCard")).convert();
		event_Noise = new FormatToPattern(StringRes.getString("messaging.noise")).convert();
		event_PlayerLocated = new FormatToPattern(StringRes.getString("messaging.disclosePlayerPosition")).convert();
		event_Attack = new FormatToPattern(StringRes.getString("messaging.playerAttacking")).convert();
		event_Death = new FormatToPattern(StringRes.getString("messaging.playerDied")).convert(); 
		event_EndGame = new FormatToPattern(StringRes.getString("messaging.endOfTheGame")).convert();
		event_Defense = new FormatToPattern(StringRes.getString("messaging.playerDefended")).convert();
		event_EndOfResults = new FormatToPattern(StringRes.getString("messaging.endOfResults")).convert();
		
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
	 *(info, TurnRequests, events, exceptions) */
	@Override
	protected void processMessage(String message) {
		Matcher map = setGameMap.matcher(message);
		Matcher startmotd = getMOTDstart.matcher(message);
		Matcher gameStartETA = startInXSeconds.matcher(message);
		Matcher chatMsg = inboundChatMessage.matcher(message);	
		
		if (!handlingMOTDspecialCase(message)) {
			
			if (map.matches()) {
				String mapname = map.group(1);
				setMap(mapname);
				
			} else if (startmotd.matches()) {
				startReadingMotd();
				
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
	
	private boolean processInfo(String message) {
		Matcher currentTurnAndPlayer = info_currentTurnAndPlayer.matcher(message);
		Matcher playerRename = info_playerRenamed.matcher(message);
		Matcher myName = info_whoIAm.matcher(message);
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
			
		} else if (myName.matches()) {
			String myNewName = myName.group(1);
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
	
	
	private boolean processTurnRequest(String message) {
		
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
		
		if (turnEnd.matches()) {
			view.clearNoisesFromMap();
			view.setTurnStatusString("waiting for my turn");
			return true;
			
		} else if (turnStart.matches()) {
			LOG.finer("My turn");
			view.setTurnStatusString("now is my turn to play");
			model.getMyPlayerState().setMyName(turnStart.group(1));
			model.getMyPlayerState().setLocation(turnStart.group(2));
			model.finishedUpdating();
			view.clearOtherPlayersFromMap();
			view.focusOnLocation(model.getMyPlayerState().getLocation(), 2000);
			// we could do more (i.e. send a visual notification of some sort)
			return true;
			
		}   else if (movement.matches()) {
			LOG.finer("Server asked to move");
			view.notifyUser("Please move your character: click where you want to go");
			view.bindPositionSender();
			return true;
			
		}  else if (askForAttack.matches()|| askForObject.matches()) {
			LOG.finer("Server asked yes/no question");
			view.relayYesNoDialog(message);
			return true;
			
		} else if (askForNoisePos.matches()) {
			LOG.finer("Server asked to place a noise");
			view.notifyUser("Select the sector you want to make a noise in");
			view.bindPositionSender();
			return true;
			
		} else if (askForLightsPos.matches()) {
			LOG.finer("Server asked where to turn the Lights on");
			view.notifyUser("Select the sector where you want to turn the lights on");
			view.bindPositionSender();
			return true;
			
		}else if (whichobjectCard.matches()) {
			LOG.finer("Server asked an object card");
			view.relayObjectCard();
			return true;
			
		} else if (discard.matches()) {
			LOG.finer("Server asked to discard a card");
			view.relayObjectCard();
			return true;
			
		} else if (playOrDiscard.matches()) {
			LOG.finer("Server asked to play or discard a card");
			view.relayYesNoDialog(message, "play", "discard");
			return true;
			
		} 
		return false;
	}
	
	private boolean processEvent(String message) {
		
		Matcher eventNoise = event_Noise.matcher(message);
		Matcher eventObject = event_ObjectUsed.matcher(message);
		Matcher eventAttack = event_Attack.matcher(message);
		Matcher eventDeath = event_Death.matcher(message);
		Matcher eventEndGame = event_EndGame.matcher(message);
		Matcher eventFoundPlr = event_PlayerLocated.matcher(message);
		Matcher eventDefense = event_Defense.matcher(message);
		Matcher endResults = event_EndOfResults.matcher(message);
		
		if (eventObject.matches()) {
			if (!isMe(eventObject.group(1))) { // it's not me
				view.notifyUser(message);
			}
			if(isMe(eventObject.group(1))) { 	//if it's me
				String cardName = getCardGUIKey(eventObject.group(2));
				model.getMyPlayerState().removeCard(cardName);	//remove the card from my hand in the Client
				model.finishedUpdating();
			}
			return true;
			
		} else if (eventAttack.matches()) {
			if (!isMe(eventAttack.group(1))) { // it's not me
				view.notifyUser(message);
			}
			model.getNowPlaying().setLastNoiseLocation(eventAttack.group(2));
			model.finishedUpdating();
			return true;
			
		} else if(eventNoise.matches()) {
			model.getNowPlaying().setLastNoiseLocation(eventNoise.group(1));
			model.finishedUpdating();
			view.addNoiseToMap(eventNoise.group(1));
			view.focusOnLocation(eventNoise.group(1), 0);
			return true;
			
		} else if (eventDeath.matches()) {
			model.getSpecificPlayerState(eventDeath.group(1)).setMyStatus(CurrentPlayerStatus.DEAD);
			model.finishedUpdating();
			view.notifyUser(message);
			return true;
			
		} else if (eventEndGame.matches()) {
			model.setGameStatus(GameStatus.FINISHED);
			model.finishedUpdating();
			return true;
			
		} else if (endResults.matches()) {
			// this is the end of the end (LOL)
			// the final results dialog will be fired here
			LOG.finer("Server sent results, printing recap screen");
			view.spawnVictoryRecap(model);
			return true;
			
		} else if (eventFoundPlr.matches()) {
			if (!isMe(eventFoundPlr.group(1))) { // it's not me
				view.addOtherPlayerToMap(eventFoundPlr.group(2), eventFoundPlr.group(1));
				view.focusOnLocation(eventFoundPlr.group(2), 0);
				model.getSpecificPlayerState(eventFoundPlr.group(1)).setLastNoiseLocation(eventFoundPlr.group(2));
				model.finishedUpdating();
			}		
			return true;
		} else if (eventDefense.matches()) {
			view.notifyUser(message);
		}
		
		return false;
	}
	
	private boolean processException(String message) {
		
		Matcher moveCanNotEnter = exception_1.matcher(message);
		Matcher moveUnreachable = exception_2.matcher(message);
		Matcher wrongCard = exception_3.matcher(message);
		
		if (moveCanNotEnter.matches() || moveUnreachable.matches()) {
			LOG.finer("Server reported : movement is impossible." );
			view.notifyUser(message);
			processMessage(StringRes.getString("messaging.timeToMove"));
			return true;
		
		} else if (wrongCard.matches()) {
			LOG.finer("Server reported : that Card can't be played now." );
			view.notifyUser(message);
			processMessage(StringRes.getString("messaging.askPlayObjectCard"));
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
				view.displayServerMOTD(loadedMotd);;
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
	
	private void setMap(String mapname) {
		LOG.finer("Setting map to " +mapname);
		view.setGameMap(mapname);
	}
	
	private void startReadingMotd() {
		LOG.finer("Server has begun writing motd");
		readingMotd = true;
	}
	
	private void visualizeChatMsg(String author, String msg) {
		view.newChatMessage(author, msg);
	}
	
	private void setStartETA(String message) {
		LOG.finer("Setting game start ETA");
		model.setGameStatus(GameStatus.GOING_TO_START);
		model.finishedUpdating();
		view.setTurnStatusString(message);
	}
	
	
	//da processInfo(message)
	
	private void startTurn(int turnNumber, String playerName) {
		LOG.finer("Someone's turn");
		view.setTurnStatusString(playerName + " is playing");
		model.updateNowPlaying(playerName);
		model.updatePlayerStatus(model.getNowPlaying().getMyName(), CurrentPlayerStatus.ALIVE);
		model.setGameStatus(GameStatus.RUNNING);
		model.setTurnNumber(turnNumber);
		model.finishedUpdating();
	}
	
	private void renamePlayer(String previousName, String changedName) {
		LOG.finer("Someone renamed himself");
		model.updatePlayerRename(previousName, changedName);
		model.finishedUpdating();
	}
	
	private void renameMyself(String myNewName) {
		LOG.finer("Read player name from server: " +myNewName);
		model.getMyPlayerState().setMyName(myNewName);
		model.finishedUpdating();
	}
	
	private void setMyPosition(String myPos) {
		LOG.finer("Read player position from server: [" + myPos + "]");
		model.getMyPlayerState().setLocation(myPos);
		model.finishedUpdating();
	}
	
	private void setMyTeam(String teamName) {
		LOG.finer("Read team name from server");
		model.getMyPlayerState().setMyTeam(teamName);
		model.finishedUpdating();
		view.discoverMyName();  // if someone else's playing we don't know it yet
	}
	
	private void drawnCard(String cardClassName) {
		LOG.finer("Server reported new object card " + cardClassName);
		String cardKey = getCardGUIKey(cardClassName);
		model.getMyPlayerState().addCard(cardKey);
		model.finishedUpdating();
		view.notifyNewCard(cardKey);
	}
	
	private void discardedCard(String cardName) {
		model.getMyPlayerState().removeCard(cardName);
		model.finishedUpdating();
	}
	
	private void playerDisconnected(String playerName) {
		model.getSpecificPlayerState(playerName).setMyStatus(CurrentPlayerStatus.DISCONNECTED);
		model.finishedUpdating();
	}
	
	private void setWinners(String team, String winnersNames) {
		LOG.finer("Server listed the winners");
		model.getVictoryState().addWinners(team, winnersNames);
		model.finalRefreshPlayerStatus();
		model.finishedUpdating();
	}
	
	private void setLoserTeam(String teamName) {
		LOG.finer("Server listed the losers");
		model.getVictoryState().setTeamDefeated(teamName);
		model.finalRefreshPlayerStatus();
		model.finishedUpdating();
	}
	
	//da processTurnRequest(msg)
	
	private void notMyTurn() {
		
	}
	
	private void myTurnStart() {
		
	}
	
	private void myMovement() {
		
	}
	
	private void askForAttack() {
		
	}
	
	private void askForNoisePosition() {
		
	}
	
	private void askForLightPosition() {
		
	}
	
	private void whichObjectCard() {
		
	}
	
	private void haveToDiscard() {
		
	}
	
	private void playOrDiscard() {
		
	}
	
	
	//da processEvent(msg)
	
	private void eventObject() {
		
	}
	
	private void eventAttack() {
		
	}
	
	private void eventNoise() {
		
	}
	
	private void eventDeath() {
		
	}
	
	private void eventEndGame() {
		
	}
	
	private void endResults() {
		
	}
	
	private void eventFoundPlayer() {
		
	}
	
	private void eventDefense() {
		
	}
	
	//da processException(msg) n:Not working properly right now
	
	private void movementException() { 
		
	}
	
	private void WrongCardException() {
		
	}
}
