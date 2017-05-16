package messagerie.serveur.utilisateur;

import messagerie.serveur.Session;
import messagerie.serveur.discussion.Discussion;
import messagerie.serveur.discussion.DiscussionTexte;
import messagerie.serveur.discussion.Message;
import messagerie.serveur.exceptions.DiscussionException;

/**
 * Classe représentant une intelligence artificielle.
 */
public class UtilisateurIA extends Utilisateur {
	public final static long serialVersionUID = 7894L;

	/**
	 * Création d'un utilisateur.
	 * @param pseudonyme Pseudonyme de l'utilisateur.
	 * @param nom Nom de l'utilisateur.
	 * @param prenom Prénom de l'utilisateur.
	 */
	public UtilisateurIA(String pseudonyme, String nom, String prenom) {
		super(pseudonyme, nom, prenom);
	}

	/**
	 * Répondre à un message.
	 * @param message Message auquel doit répondre l'intelligence artificielle.
	 * @return Réponse au message.
	 */
	public Message repondre(Message message) throws DiscussionException, Exception {
		long id = message.getId();
		Discussion discussion = Session.getApplication().getDiscussion(id);

		if (discussion instanceof DiscussionTexte)
			return new Message(this, "Wesh, bien ou bien?", (DiscussionTexte)discussion);
		else
			throw new DiscussionException("Type de discussion incompatible");
		
	}

	@Override
	public void envoyerMessage(String message) {
		
	}
}
