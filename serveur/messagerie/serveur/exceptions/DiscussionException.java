package messagerie.serveur.exception;

public class DiscussionException extends ServeurException {
	public final static long serialVersionUID = 8935L;
	
	public DiscussionException() {
		super();
	}

	public DiscussionException(String message) {
		super(message);
	}

	public DiscussionException(String message, Exception exception) {
		super(message, exception);
	}
}