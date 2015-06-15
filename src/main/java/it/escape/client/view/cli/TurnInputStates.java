package it.escape.client.view.cli;

public enum TurnInputStates {
	
	FREE("free-action"), OBJECTCARD("object-card"), 
	POSITION("coordinates"), YES_NO("yes/no"), OTHER_CHOICE("enter choice");
	
	private String prompt;

	private TurnInputStates(String prompt) {
		this.prompt = prompt;
	}
	
	public String getPrompt() {
		return prompt;
	}
}
