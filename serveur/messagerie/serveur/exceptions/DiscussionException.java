package messagerie.serveur.exception;

/**
 * Exceptions liées aux discussions.
 */
public class DiscussionException extends ServeurException {
	public final static long serialVersionUID = 8935L;
	
	/**
	 * Création de l'exception
	 */
	public DiscussionException() {
		super();
	}

	/**
	 * Création de l'exception
	 * @param message Message lié à l'exception.
	 */
	public DiscussionException(String message) {
		super(message);
	}

	/**
	 * Création de l'exception
	 * @param message Message lié à l'exception.
	 * @param exception Exception à chainer.
	 */
	public DiscussionException(String message, Exception exception) {
		super(message, exception);
	}
}