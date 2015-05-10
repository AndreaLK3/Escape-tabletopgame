package it.escape.server.model.game.exceptions;

public class BadJsonFileException extends Exception {

	private static final long serialVersionUID = 1L;

	public BadJsonFileException() {
		super();
		
	}

	public BadJsonFileException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public BadJsonFileException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BadJsonFileException(String arg0) {
		super(arg0);
	}

	public BadJsonFileException(Throwable arg0) {
		super(arg0);
	}
	
}
