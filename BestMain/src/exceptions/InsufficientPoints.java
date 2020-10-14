package exceptions;

public class InsufficientPoints extends Exception {
	private static final long serialVersionUID = 1L;

	public InsufficientPoints() {
		super();
	}

	/**
	 * This exception is triggered if the event has already finished
	 * 
	 * @param s String of the exception
	 */
	public InsufficientPoints(String s) {
		super(s);
	}
}