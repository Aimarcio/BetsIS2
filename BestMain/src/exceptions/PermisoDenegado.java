package exceptions;

public class PermisoDenegado extends Exception {
	private static final long serialVersionUID = 1L;

	public PermisoDenegado() {
		super();
	}

	/**
	 * This exception is triggered if the event has already finished
	 * 
	 * @param s String of the exception
	 */
	public PermisoDenegado(String s) {
		super(s);
	}
}