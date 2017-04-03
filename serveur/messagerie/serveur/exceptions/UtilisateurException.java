package messagerie.serveur.exception;

public class UtilisateurException extends ServeurException {
	public final static long serialVersionUID = 7535L;
	
	public UtilisateurException() {
		super();
	}

	public UtilisateurException(String message) {
		super(message);
	}

	public UtilisateurException(String message, Exception exception) {
		super(message, exception);
	}
}