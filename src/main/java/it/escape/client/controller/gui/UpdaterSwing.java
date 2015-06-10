package it.escape.client.controller.gui;

import it.escape.client.controller.MessageCarrier;
import it.escape.client.controller.Updater;
import it.escape.client.model.CurrentPlayerStatus;
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
 * TODO: add facilities to modify the local state (the model), when needed.
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
	private Pattern info_yourTeam;
	private Pattern info_currentTurnAndPlayer;
	private Pattern info_playerRenamed;
	private Pattern info_DrawnObjectCard;
	private Pattern info_whoIAm;
	private Pattern info_whereIAm;
	
	private Pattern turn_askForAttack;
	private Pattern turn_askForNoisePos;
	private Pattern turn_movement;
	private Pattern turn_askForObject;
	
	private Pattern event_Noise;
	private Pattern event_ObjectUsed;
	private Pattern event_PlayerLocated;
	private Pattern event_Attack;
	private Pattern event_Death;
	
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
		info_yourTeam = new FormatToPattern(StringRes.getString("messaging.gamemaster.playAs")).convert();
		info_currentTurnAndPlayer = new FormatToPattern(StringRes.getString("messaging.timecontroller.turnNumber")).convert();
		info_DrawnObjectCard = new FormatToPattern(StringRes.getString("messaging.objectCardDrawn")).convert();
		info_playerRenamed  = new FormatToPattern(StringRes.getString("messaging.announceRename")).convert();
		info_whoIAm = new FormatToPattern(StringRes.getString("messaging.whoYouAre")).convert();
		info_whereIAm = new FormatToPattern(StringRes.getString("messaging.hereYouAre")).convert();
		
		turn_askForObject = new FormatToPattern(StringRes.getString("messaging.askPlayObjectCard")).convert();
		turn_askForAttack = new FormatToPattern(StringRes.getString("messaging.askIfAttack")).convert();
		turn_askForNoisePos = new FormatToPattern(StringRes.getString("messaging.askForNoisePosition")).convert();
		turn_movement = new FormatToPattern(StringRes.getString("messaging.timeToMove")).convert();
		
		event_ObjectUsed = new FormatToPattern(StringRes.getString("messaging.playerIsUsingObjCard")).convert();
		event_Noise = new FormatToPattern(StringRes.getString("messaging.noise")).convert();
		event_PlayerLocated = new FormatToPattern(StringRes.getString("messaging.disclosePlayerPosition")).convert();
		event_Attack = new FormatToPattern(StringRes.getString("messaging.playerAttacking")).convert();
		event_Death = new FormatToPattern(StringRes.getString("messaging.playerDied")).convert(); 
		
		//These ones are necessary so that we can display the JInputDialog (ex, for the position) again.
		//There will be a dialog that displays the exception message (whatever it is) and
		//the view will have to repeat the last dialog display...
		exception_1 = new FormatToPattern(StringRes.getString("messaging.exceptions.playerCanNotEnter")).convert();
		exception_2 = new FormatToPattern(StringRes.getString("messaging.exceptions.destinationUnreachable")).convert();
		exception_3 = new FormatToPattern(StringRes.getString("messaging.exceptions.wrongCard")).convert();
		
	}
	
	
	/**The method that is invoked whenever this class receives a motifyObserves(),
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
				LOG.finer("Setting map to " + map.group(1));
				view.setGameMap(map.group(1));
				
			} else if (startmotd.matches()) {
				LOG.finer("Server has begun writing motd");
				readingMotd = true;
				
			} else if (chatMsg.matches()) {
				view.newChatMessage(chatMsg.group(1), chatMsg.group(2));
				
			} else if (gameStartETA.matches()) {
				LOG.finer("Setting game start ETA");
				view.setTurnStatusString(message);
				
			} 
			processInfo(message);
			processTurnRequest(message);
			processEvent(message);
			processException(message);
			
		}  
		
	}
	
	private void processInfo(String message) {
		Matcher currentTurnAndPlayer = info_currentTurnAndPlayer.matcher(message);
		Matcher playerRename = info_playerRenamed.matcher(message);
		Matcher myName = info_whoIAm.matcher(message);
		Matcher myTeam = info_yourTeam.matcher(message);
		Matcher myPosition = info_whereIAm.matcher(message);
		Matcher drawncard = info_DrawnObjectCard.matcher(message);
		
		if (currentTurnAndPlayer.matches()) {
			LOG.finer("Someone's turn");
			view.setTurnStatusString(currentTurnAndPlayer.group(2) + " is playing");
			model.updatePlayerExists(currentTurnAndPlayer.group(2));
			model.setTurnNumber(Integer.parseInt(currentTurnAndPlayer.group(1)));
			model.finishedUpdating();
			
		} else if (playerRename.matches()) {
			LOG.finer("Someone renamed himself");
			model.updatePlayerRename(playerRename.group(1), playerRename.group(2));
			model.finishedUpdating();
			
		} else if (myName.matches()) {
			LOG.finer("Read player name from server");
			model.getMyPlayerState().setMyName(myName.group(1));
			model.finishedUpdating();
			
		} else if (myPosition.matches()) {
			LOG.finer("Read player position from server: [" + myPosition.group(1) + "]");
			model.getMyPlayerState().setLocation(myPosition.group(1));
			model.finishedUpdating();
			
		} else if (myTeam.matches()) {
			LOG.finer("Read team name from server");
			model.getMyPlayerState().setMyTeam(myTeam.group(1));
			model.finishedUpdating();
			view.discoverMyName();  // if someone else's playing we don't know it yet
			
		} else if (drawncard.matches()) {
			LOG.finer("Server reported new object card " + drawncard.group(1));
			String cardKey = getCardGUIKey(drawncard.group(1));
			model.getMyPlayerState().addCard(cardKey);
			model.finishedUpdating();
			view.notifyUser("You have drawn a " + cardKey + " card");
			
		} 
		
		
	}
	
	private void processTurnRequest(String message) {
		
		Matcher turnStart = turn_Start.matcher(message);
		Matcher movement = turn_movement.matcher(message);
		Matcher askForAttack = turn_askForAttack.matcher(message);
		Matcher askForObject = turn_askForObject.matcher(message);
		Matcher whichobjectCard = input_ObjectCard.matcher(message);
		Matcher askForNoisePos = turn_askForNoisePos.matcher(message);
		Matcher turnEnd = turn_End.matcher(message);
		
		
		if (turnEnd.matches()) {
			view.clearNoisesFromMap();
			view.setTurnStatusString("waiting for my turn");
			
		} else if (turnStart.matches()) {
			LOG.finer("My turn");
			view.setTurnStatusString("now is my turn to play");
			model.getMyPlayerState().setMyName(turnStart.group(1));
			model.getMyPlayerState().setLocation(turnStart.group(2));
			model.finishedUpdating();
			// we could do more (i.e. send a visual notification of some sort)
			
		}   else if (movement.matches()) {
			LOG.finer("Server asked to move");
			view.notifyUser("Please move your character: click where you want to go");
			view.bindPositionSender();
			
		}  else if (askForAttack.matches()|| askForObject.matches()) {
			LOG.finer("Server asked yes/no question");
			view.relayYesNoDialog(message);
			
		} else if (askForNoisePos.matches()) {
			LOG.finer("Server asked to place a noise");
			view.notifyUser("Select the sector you want to make a noise in");
			view.bindPositionSender();
			
		} else if (whichobjectCard.matches()) {
			LOG.finer("Server asked an object card");
			view.relayObjectCard();
			
		 } 
	}
	
	private void processEvent(String message) {
		
		Matcher eventNoise = event_Noise.matcher(message);
		Matcher eventObject = event_ObjectUsed.matcher(message);
		Matcher eventAttack = event_Attack.matcher(message);
		Matcher eventDeath = event_Death.matcher(message);
		
		if(eventObject.matches() || eventAttack.matches()) {
			view.notifyUser(message);
			
		} else if(eventNoise.matches()) {
			view.addNoiseToMap(eventNoise.group(1));
			
		} else if (eventDeath.matches()) {
			model.getSpecificPlayerState(eventDeath.group(1)).setMyStatus(CurrentPlayerStatus.DEAD);
			view.notifyUser(message);
		}
	
	}
	
	private void processException(String message) {
		
		Matcher moveCanNotEnter = exception_1.matcher(message);
		Matcher moveUnreachable = exception_2.matcher(message);
		Matcher wrongCard = exception_3.matcher(message);
		
	if (moveCanNotEnter.matches() || moveUnreachable.matches()) {
		LOG.finer("Server reported : movement is impossible." );
		view.notifyUser(message);
		processMessage(StringRes.getString("messaging.timeToMove"));
	
	} else if (wrongCard.matches()) {
		LOG.finer("Server reported : that Card can't be played now." );
		view.notifyUser(message);
		processMessage(StringRes.getString("messaging.askPlayObjectCard"));
		
	}
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
}
