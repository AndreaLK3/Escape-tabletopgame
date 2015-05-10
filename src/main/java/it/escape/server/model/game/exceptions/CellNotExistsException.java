package it.escape.server.model.game.exceptions;

public class CellNotExistsException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public CellNotExistsException() {
		super();
	}
	
	public CellNotExistsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CellNotExistsException(String arg0) {
		super(arg0);
	}

	public CellNotExistsException(Throwable arg0) {
		super(arg0);
	}
	
}
