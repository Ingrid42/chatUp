package messagerie.serveur.exception;

/**
 * Exceptions liées aux utilisateurs.
 */
public class UtilisateurException extends ServeurException {
	public final static long serialVersionUID = 7535L;

	/**
	 * Création de l'exception
	 */	
	public UtilisateurException() {
		super();
	}

	/**
	 * Création de l'exception
	 * @param message Message lié à l'exception.
	 */
	public UtilisateurException(String message) {
		super(message);
	}

	/**
	 * Création de l'exception
	 * @param message Message lié à l'exception.
	 * @param exception Exception à chainer.
	 */
	public UtilisateurException(String message, Exception exception) {
		super(message, exception);
	}
}