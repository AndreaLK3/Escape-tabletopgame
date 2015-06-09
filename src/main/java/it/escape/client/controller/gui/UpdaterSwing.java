package it.escape.client.controller.gui;

import it.escape.client.controller.MessageCarrier;
import it.escape.client.controller.Updater;
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

	private UpdaterSwingToDisplayerInterface view;
	
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
	
	private Pattern turn_Attack;
	private Pattern turn_askForNoise;
	
	private Pattern event_Noise;
	private Pattern event_ObjectUsed;
	private Pattern event_PlayerLocated;
	private Pattern event_Attack;
	private Pattern event_Death;
	
	private Pattern exception_1;
	private Pattern exception_2;
	private Pattern exception_3;
	private Pattern exception_4;
	private Pattern exception_5;

	
	
	
	public UpdaterSwing(ModelForGUI model) {
		super();
		LogHelper.setDefaultOptions(LOG);
		this.model = model;
	}

	public void bindView(UpdaterSwingToDisplayerInterface view) {
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
		
		turn_Attack = new FormatToPattern(StringRes.getString("messaging.askIfAttack")).convert();
		turn_askForNoise = new FormatToPattern(StringRes.getString("messaging.askForNoisePosition")).convert();
		
		event_ObjectUsed = new FormatToPattern(StringRes.getString("messaging.playerIsUsingObjCard")).convert();
		event_Noise = new FormatToPattern(StringRes.getString("messaging.noise")).convert();
		event_PlayerLocated = new FormatToPattern(StringRes.getString("messaging.disclosePlayerPosition")).convert();
		event_Attack = new FormatToPattern(StringRes.getString("messaging.playerAttacking")).convert();
		event_Death = new FormatToPattern(StringRes.getString("messaging.playerDied")).convert(); 
		
		//These ones are necessary so that we can display the JInputDialog (ex, for the position) again.
		//There will be a dialog that displays the exception message (whatever it is) and
		//the view will have to repeat the last dialog display...
		exception_1 = new FormatToPattern(StringRes.getString("messaging.exceptions.badCoordinatesFormat")).convert();
		exception_2 = new FormatToPattern(StringRes.getString("messaging.exceptions.cellNotExists")).convert();
		exception_3 = new FormatToPattern(StringRes.getString("messaging.exceptions.playerCanNotEnter")).convert();
		exception_4 = new FormatToPattern(StringRes.getString("messaging.exceptions.destinationUnreachable")).convert();
		exception_5 = new FormatToPattern(StringRes.getString("messaging.exceptions.wrongCard")).convert();
		
	}
	
	
	@Override
	protected void processMessage(String message) {
		Matcher map = setGameMap.matcher(message);
		Matcher startmotd = getMOTDstart.matcher(message);
		Matcher gameStartETA = startInXSeconds.matcher(message);
		Matcher chatMsg = inboundChatMessage.matcher(message);
		Matcher turnEnd = myTurnEnd.matcher(message);
		Matcher turnStart = myTurnStart.matcher(message);
		Matcher othersTurn = info_currentTurnAndPlayer.matcher(message);
		Matcher playerRename = info_playerRenamed.matcher(message);
		Matcher myName = info_whoIAm.matcher(message);
		Matcher myTeam = info_yourTeam.matcher(message);
		
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
				view.setTurnStatusString(message);
			} else if (turnEnd.matches()) {
				view.setTurnStatusString("waiting for my turn");
			} else if (othersTurn.matches()) {
				view.setTurnStatusString(othersTurn.group(2) + " is playing");
				// don't add *myself* to the list of *others*
				if (!othersTurn.group(2).equals(model.getMyPlayerState().getMyName())) {
					model.updatePlayerExists(othersTurn.group(2));
				}
				model.setTurnNumber(Integer.parseInt(othersTurn.group(1)));
				model.finishedUpdating();
			} else if (turnStart.matches()) {
				view.setTurnStatusString("now is my turn to play");
				model.getMyPlayerState().setMyName(turnStart.group(1));
				model.getMyPlayerState().setLocation(turnStart.group(2));
				model.finishedUpdating();
				// we could do more (i.e. send a visual notification of some sort)
			} else if (playerRename.matches()) {
				model.updatePlayerRename(playerRename.group(1), playerRename.group(2));
				model.finishedUpdating();
			} else if (myName.matches()) {
				LOG.finer("Read player name from server");
				model.getMyPlayerState().setMyName(myName.group(1));
				model.finishedUpdating();
			} else if (myTeam.matches()) {
				LOG.finer("Read team name from server");
				model.getMyPlayerState().setMyTeam(myTeam.group(1));
				model.finishedUpdating();
				view.discoverMyName();  // if someone else's playing we don't know it yet
			} 
		}
		
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
