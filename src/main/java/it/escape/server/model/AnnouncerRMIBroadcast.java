package it.escape.server.model;

import it.escape.client.connection.rmi.ClientRemoteInterface;
import it.escape.client.controller.gui.UpdaterSwing;
import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.model.game.cards.ObjectCard;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.JoinPlayerList;
import it.escape.server.model.game.players.Player;
import it.escape.server.model.game.players.PlayerTeams;
import it.escape.strings.StringRes;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Announcer based on the RMI system, instead of being an observable
 * carrying a message string, it directly invokes specific
 * remote methods in everyone among its subscribers.
 * @author michele, andrea
 *
 */
public class AnnouncerRMIBroadcast implements Announcer {
	
	private static final Logger LOG = Logger.getLogger( AnnouncerRMIBroadcast.class.getName() );
	
	private List<ClientRemoteInterface> subscribed;
	
	public AnnouncerRMIBroadcast() {
		subscribed = new ArrayList<ClientRemoteInterface>();
	}
	
	public void subscribe(ClientRemoteInterface client) {
		subscribed.add(client);
	}
	
	public void unSubscribe(ClientRemoteInterface client) {
		subscribed.remove(client);
	}

	@Override
	public void announcePlayerConnected(int connected, int maximum) {
		for (ClientRemoteInterface client : subscribed) {
			try {
				client.playerConnected(connected, maximum);
			} catch (RemoteException e) {
				LOG.warning("cannot announce player connection: " + e.getMessage());
			}
		}
	}

	@Override
	public void announcePlayerDisconnected(PlayerActionInterface player) {
		for (ClientRemoteInterface client : subscribed) {
			
		}

	}

	@Override
	public void announceAttack(PlayerActionInterface player,PositionCubic position) {
		
		String attacker = player.getName();
		String location = CoordinatesConverter.fromCubicToAlphaNum(position);
		
		for (ClientRemoteInterface client : subscribed) {
			try {
				client.eventAttack(attacker, location);
			} catch (RemoteException e) {
				LOG.warning("cannot announce attack: " + e.getMessage());
			}
		}

	}

	@Override
	public void announceNoise(String location) {
		location = CoordinatesConverter.prettifyAlphaNum(location);
		for (ClientRemoteInterface client : subscribed) {
			try {
				client.eventNoise(location);
			} catch (RemoteException e) {
				LOG.warning("cannot announce noise: " + e.getMessage());
			}
		}

	}

	@Override
	public void announceObjectCard(PlayerActionInterface player, ObjectCard theCard) {
		String playerName = player.getName();
		String cardClassName = theCard.getClass().getSimpleName();
		
		for (ClientRemoteInterface client : subscribed) {
			try {
				client.eventObject(playerName, cardClassName);
			} catch (RemoteException e) {
				LOG.warning("cannot announce player using object card: " + e.getMessage());
			}
		}

	}

	@Override
	public void announceDeath(PlayerActionInterface victim) {
		String playerKilled = victim.getName();
		for (ClientRemoteInterface client : subscribed) {
			try {
				client.eventDeath(playerKilled);
			} catch (RemoteException e) {
				LOG.warning("cannot announce player's death: " + e.getMessage());
			}
		}

	}

	@Override
	public void announceDefense(PositionCubic position) {
		String alphaNumPos = CoordinatesConverter.fromCubicToAlphaNum(position);
		for (ClientRemoteInterface client : subscribed) {
			try {
				client.eventDefense(alphaNumPos);
			} catch (RemoteException e) {
				LOG.warning("cannot announce player's defense: " + e.getMessage());
			}
		}

	}

	/**Currently not used in the AnnouncerRMI; it is here because it is needed in the interface implementation*/
	@Override
	public void announce(String message) {}

	@Override
	public void announcePlayerPosition(PlayerActionInterface p,	PositionCubic position) {
		String playerName = p.getName();
		String location = CoordinatesConverter.fromCubicToAlphaNum(position);
		for (ClientRemoteInterface client : subscribed) {
			try {
				client.eventFoundPlayer(playerName, location);
			} catch (RemoteException e) {
				LOG.warning("cannot announce player's position: " + e.getMessage());
			}
		}

	}

	@Override
	public void announceEscape(PlayerActionInterface currentPlayer) {
		String playerName = currentPlayer.getName();
		for (ClientRemoteInterface client : subscribed) {
			try {
				client.eventPlayerEscaped(playerName);
			} catch (RemoteException e) {
				LOG.warning("cannot announce player escaped: " + e.getMessage());
			}
		}
		

	}

	@Override
	public void announceGameEnd() {
		for (ClientRemoteInterface client : subscribed) {
			try {
				client.eventEndGame();
			} catch (RemoteException e) {
				LOG.warning("cannot announce game end: " + e.getMessage());
			};
		}

	}

	@Override
	public void announceEndOfResults() {
		for (ClientRemoteInterface client : subscribed) {
			try {
				client.endResults();
			} catch (RemoteException e) {
				LOG.warning("cannot announce end of results: " + e.getMessage());
			}
		}

	}

	@Override
	public void announceTeamVictory(PlayerTeams team, List<Player> members) {
		String teamName = team.toString();
		String winnersNames = new JoinPlayerList(members).join(StringRes.getString("messaging.playerListSeparator"));
		for (ClientRemoteInterface client : subscribed) {
			try {
				client.setWinners(teamName, winnersNames);
			} catch (RemoteException e) {
				LOG.warning("cannot announce winner team: " + e.getMessage());
			}
			
		}

	}

	@Override
	public void announceTeamDefeat(PlayerTeams team) {
		String teamName = team.toString();
		for (ClientRemoteInterface client : subscribed) {
			try {
				client.setLoserTeam(teamName);
			} catch (RemoteException e) {
				LOG.warning("cannot announce loser team: " + e.getMessage());
			}
		}

	}

	@Override
	public void announcePlayerRename(String oldname, String newname) {
		for (ClientRemoteInterface client : subscribed) {
			try {
				client.renamePlayer(oldname, newname);
			} catch (RemoteException e) {
				LOG.warning("cannot announce player name: " + e.getMessage());
			};
		}

	}

	@Override
	public void announceChatMessage(PlayerActionInterface player, String message) {
		String author = player.getName();
		for (ClientRemoteInterface client : subscribed) {
			try {
				client.visualizeChatMsg(author, message);
			} catch (RemoteException e) {
				LOG.warning("cannot announce chat message: " + e.getMessage());
			}
		}

	}

	@Override
	public void announceGameStartETA(int seconds) {
		String message = String.format(	StringRes.getString("messaging.gameStartETA"),seconds);
		for (ClientRemoteInterface client : subscribed) {
			try {
				client.setStartETA(message);
			} catch (RemoteException e) {
				LOG.warning("cannot announce game start ETA: " + e.getMessage());
			}
		}

	}

	@Override
	public void announceNewTurn(int turnNumber, String playerName) {
		for (ClientRemoteInterface client : subscribed) {
			try {
				client.startTurn(turnNumber, playerName);;
			} catch (RemoteException e) {
				LOG.warning("cannot announce game start ETA: " + e.getMessage());
			}
		}
	}

}
