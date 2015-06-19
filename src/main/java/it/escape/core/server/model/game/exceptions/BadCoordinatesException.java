package it.escape.core.server.model.game.exceptions;

public class BadCoordinatesException extends Exception {

	private static final long serialVersionUID = 1L;

	public BadCoordinatesException() {
		super();
	}

	public BadCoordinatesException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public BadCoordinatesException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BadCoordinatesException(String arg0) {
		super(arg0);
	}

	public BadCoordinatesException(Throwable arg0) {
		super(arg0);
	}
	
}
