package it.escape.server.model.game.exceptions;

public class DestinationUnreachableException extends Exception {

	
	private static final long serialVersionUID = 1L;

	public DestinationUnreachableException() {
		
	}

	public DestinationUnreachableException(String message) {
		super(message);
	
	}

	public DestinationUnreachableException(Throwable cause) {
		super(cause);
		
	}

	public DestinationUnreachableException(String message, Throwable cause) {
		super(message, cause);
	
	}

	public DestinationUnreachableException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	
	}

}
