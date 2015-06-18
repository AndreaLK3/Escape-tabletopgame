package it.escape.client.controller;

public class RelayForSocket extends Relay {

	public RelayForSocket(ClientChannelInterface communication) {
		super(communication);
	}

	/**This method simply sends the String over to the Server through the established Socket,
	 * via invoking the sendMessage method in ClientSocketChannel*/
	@Override
	public void relayMessage(String message) {
		communication.sendMessage(message);
	}

}
