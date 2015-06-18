package it.escape.client.controller;

public class RelayForSocket extends Relay {

	public RelayForSocket(ClientChannelInterface communication) {
		super(communication);
	}

	@Override
	public void relayMessage(String message) {
		communication.sendMessage(message);
	}

}
