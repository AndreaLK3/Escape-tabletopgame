package it.escape.server.controller;

import java.util.List;

public interface MessagingHead {
	
	public void headWrite(String message);
	
	public String headRead();
	
	public void setContext(List<String> context);
	
	public void setDefaultOption(String defaultOption);
	
	public void overrideDefaultOption();
}
