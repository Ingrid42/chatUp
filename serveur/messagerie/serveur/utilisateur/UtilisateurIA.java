package messagerie.serveur.utilisateur;

import messagerie.serveur.discussion.Message;

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
	public Message repondre(Message message) {
		// TODO a modifier id de la discussion
		return new Message(this, "Wesh, bien ou bien?", message.getId());
	}
}
