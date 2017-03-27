package messagerie.serveur.utilisateur;

import messagerie.serveur.discussion.Message;

public class UtilisateurIA extends Utilisateur {
	public final static long serialVersionUID = 7894L;

	public UtilisateurIA(String pseudonyme, String nom, String prenom) {
		super(pseudonyme, nom, prenom); 
	}

	public Message repondre(Message message) {
		return new Message(this, "Wesh bien ou bien?");
	}
}