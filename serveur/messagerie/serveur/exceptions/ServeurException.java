package messagerie.serveur.exceptions;

/**
 * Exceptions liées au serveur. Cette classe ne peut être instanciée.
 * Il est donc nécessaire de créer des classes héritées pour tous types d'exceptions liés au serveur.
 */
public abstract class ServeurException extends Exception {
	public final static long serialVersionUID = 35421L;

	/**
	 * Création de l'exception
	 */
	public ServeurException() {
		super();
	}

	/**
	 * Création de l'exception
	 * @param message Message lié à l'exception.
	 */
	public ServeurException(String message) {
		super(message);
	}

	/**
	 * Création de l'exception
	 * @param message Message lié à l'exception.
	 * @param exception Exception à chainer.
	 */
	public ServeurException(String message, Exception exception) {
		super(message, exception);
	}
}