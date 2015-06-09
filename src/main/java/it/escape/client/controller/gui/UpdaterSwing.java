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
 * Updates the swing interface when a new network message arrives
 * TODO: add facilities to modify the local state (the model) too,
 * when needed.
 * @author michele
 *
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
	private Pattern turn_Attack;
	private Pattern event_Noise;
	private Pattern ask_Noise;
	private Pattern turn_askForNoise;
	private Pattern info_DrawnObjectCard;
	private Pattern event_ObjectUsed;
	private Pattern event_PlayerLocated;
	private Pattern event_Attack;
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
		
		turn_Attack = new FormatToPattern(StringRes.getString("messaging.askIfAttack")).convert();
		turn_askForNoise = new FormatToPattern(StringRes.getString("messaging.askForNoisePosition")).convert();
		
		event_ObjectUsed = new FormatToPattern(StringRes.getString("messaging.playerIsUsingObjCard")).convert();
		event_Noise = new FormatToPattern(StringRes.getString("messaging.noise")).convert();
		event_PlayerLocated = new FormatToPattern(StringRes.getString("messaging.disclosePlayerPosition")).convert();
		event_Attack = new FormatToPattern(StringRes.getString("messaging.playerAttacking")).convert();
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
			} else if (turnStart.matches()) {
				view.setTurnStatusString("now is my turn to play");
				// we could do more (i.e. send a visual notification of some sort)
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
