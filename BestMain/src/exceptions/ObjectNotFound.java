package exceptions;

public class ObjectNotFound extends Exception {
	private static final long serialVersionUID = 1L;

	public ObjectNotFound() {
		super();
	}

	/**
	 * This exception is triggered if the event has already finished
	 * 
	 * @param s String of the exception
	 */
	public ObjectNotFound(String s) {
		super(s);
	}
}