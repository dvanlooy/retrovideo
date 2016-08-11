package be.vdab.exceptions;

public class RetroException extends Exception {
	private static final long serialVersionUID = 1L;

	public RetroException() {
		super();

	}

	public RetroException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		
	}

	public RetroException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	
	}

	public RetroException(String arg0) {
		super(arg0);
	
	}

	public RetroException(Throwable arg0) {
		super(arg0);
	
	}
	
	
}
