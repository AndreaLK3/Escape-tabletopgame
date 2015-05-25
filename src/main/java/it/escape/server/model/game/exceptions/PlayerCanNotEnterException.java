package it.escape.server.model.game.exceptions;

public class PlayerCanNotEnterException extends Exception {


	private static final long serialVersionUID = 1L;

	public PlayerCanNotEnterException() {
	
	}

	public PlayerCanNotEnterException(String message) {
		super(message);
		
	}

	public PlayerCanNotEnterException(Throwable cause) {
		super(cause);
		
	}

	public PlayerCanNotEnterException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public PlayerCanNotEnterException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
