package it.escape.server.model.game.exceptions;

public class WrongCardException extends Exception {

	private static final long serialVersionUID = 1L;

	public WrongCardException() {
		// TODO Auto-generated constructor stub
	}

	public WrongCardException(String message) {
		super(message);
		
	}

	public WrongCardException(Throwable cause) {
		super(cause);
		
	}

	public WrongCardException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public WrongCardException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
