package it.escape.core.client.model;

public enum CurrentPlayerStatus {

	ALIVE(2), DEAD(3), CONNECTED(1), DISCONNECTED(1), WINNER(4), LOSER(4);
	
	private int priorityLevel;
	
	private CurrentPlayerStatus(int priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	public int getPriorityLevel() {
		return priorityLevel;
	}
}
