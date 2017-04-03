package messagerie.serveur.exception;

public abstract class ServeurException extends Exception {
	public final static long serialVersionUID = 35421L;

	public ServeurException() {
		super();
	}

	public ServeurException(String message) {
		super(message);
	}

	public ServeurException(String message, Exception exception) {
		super(message, exception);
	}
}