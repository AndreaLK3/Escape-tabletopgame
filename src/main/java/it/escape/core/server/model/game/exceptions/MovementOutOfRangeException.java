package it.escape.core.server.model.game.exceptions;

public class MovementOutOfRangeException extends Exception {
private static final long serialVersionUID = 1L;
	
	public MovementOutOfRangeException() {
		super();
	}
	
	public MovementOutOfRangeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public MovementOutOfRangeException(String arg0) {
		super(arg0);
	}

	public MovementOutOfRangeException(Throwable arg0) {
		super(arg0);
	}
	
}