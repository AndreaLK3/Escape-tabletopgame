package it.escape.core.client.model;

public enum CurrentPlayerStatus {

	ALIVE(2), DEAD(2), CONNECTED(1), DISCONNECTED(1), WINNER(3), LOSER(3);
	
	private int priorityLevel;
	
	private CurrentPlayerStatus(int priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	public int getPriorityLevel() {
		return priorityLevel;
	}
}
