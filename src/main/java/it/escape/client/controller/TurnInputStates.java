package it.escape.client.controller;

public enum TurnInputStates {
	
	FREE(""), OBJECTCARD("object-card"), POSITION("coordinates"), YES_NO("yes/no");
	
	private String prompt;

	private TurnInputStates(String prompt) {
		this.prompt = prompt;
	}
	
	public String getPrompt() {
		return prompt;
	}
}
