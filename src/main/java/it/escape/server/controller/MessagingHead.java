package it.escape.server.controller;

import it.escape.server.model.game.exceptions.AnswerOutOfContextException;

import java.util.List;

public interface MessagingHead {
	
	public void writeToClient(String message);
	
	public String readFromClient();
	
	public void setContext(List<String> context);
	
	public void setDefaultOption(String defaultOption);
	
	public void overrideDefaultOption();
}
