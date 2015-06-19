package it.escape.core.server.model.game.exceptions;

public class AnswerOutOfContextException extends Exception {


	private static final long serialVersionUID = 1L;

	public AnswerOutOfContextException() {
		super();
		
	}

	public AnswerOutOfContextException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	
	}

	public AnswerOutOfContextException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public AnswerOutOfContextException(String message) {
		super(message);

	}

	public AnswerOutOfContextException(Throwable cause) {
		super(cause);
		
	}

}
