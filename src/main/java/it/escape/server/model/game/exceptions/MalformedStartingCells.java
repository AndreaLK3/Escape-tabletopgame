package it.escape.server.model.game.exceptions;

public class MalformedStartingCells extends Exception {

	public MalformedStartingCells() {
		
	}

	public MalformedStartingCells(String message) {
		super(message);
	}

	public MalformedStartingCells(Throwable cause) {
		super(cause);
		
	}

	public MalformedStartingCells(String message, Throwable cause) {
		super(message, cause);
	
	}

	public MalformedStartingCells(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
