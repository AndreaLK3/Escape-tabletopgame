package it.escape.core.server.model.game.exceptions;

public class CardNotPresentException extends Exception {
 
	 
	private static final long serialVersionUID = 1L;

	public CardNotPresentException() {
		
	}

	public CardNotPresentException(String message) {
		super(message);
		
	}

	public CardNotPresentException(Throwable cause) {
		super(cause);
		
	}

	public CardNotPresentException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public CardNotPresentException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
