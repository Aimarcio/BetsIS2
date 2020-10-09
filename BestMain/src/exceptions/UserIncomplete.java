package exceptions;

public class UserIncomplete extends Exception {
	private static final long serialVersionUID = 1L;

	public UserIncomplete() {
		super();
	}

	/**
	 * This exception is triggered if the event has already finished
	 * 
	 * @param s String of the exception
	 */
	public UserIncomplete(String s) {
		super(s);
	}
}