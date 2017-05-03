package messagerie.serveur.utilisateur;

import messagerie.serveur.discussion.*;

import java.util.List;
import java.util.ArrayList;

import java.io.Serializable;

/**
 * Classe représentant un utilisateur de l'application.
 */
public abstract class Utilisateur implements Serializable {
	public final static long serialVersionUID = 951L;

	/**
	 * Pseudonyme de l'utilisateur.
	 */
	private String pseudonyme;

	/**
	 * Nom de l'utilisateur.
	 */
	private String nom;

	/**
	 * Prénom de l'utilisateur.
	 */
	private String prenom;

	/**
	 * Liste des discussions où l'utilisateur est présent.
	 */
	private List<Discussion> discussions;

	/**
	 * Création d'un utilisateur.
	 * @param pseudonyme Pseudonyme de l'utilisateur.
	 * @param nom Nom de l'utilisateur.
	 * @param prenom Prénom de l'utilisateur.
	 */
	public Utilisateur(String pseudonyme, String nom, String prenom) {
		this.pseudonyme = pseudonyme;
		this.nom = nom;
		this.prenom = prenom;

		this.discussions = new ArrayList<Discussion>();
	}

	/**
	 * Récupérer le pseudonyme de l'utilisateur.
	 * @return Pseudonyme de l'utilisateur.
	 */
	public String getPseudonyme() { return this.pseudonyme; }

	/**
	 * Récupérer le nom de l'utilisateur.
	 * @return Nom de l'utilisateur.
	 */
	public String getNom() { return this.nom; }

	/**
	 * Récupérer le prénom de l'utilisateur.
	 * @return Prénom de l'utilisateur.
	 */
	public String getPrenom() { return this.prenom; }

	/**
	 * Changer le nom de l'utilisateur.
	 * @param nom Nom de l'utilisateur.
	 * @return L'utilisateur courant.
	 */
	public Utilisateur setNom(String nom) { 
		this.nom = nom; 
		return this; 
	}
	
	/**
	 * Changer le prénom de l'utilisateur.
	 * @param prenom Prénom de l'utilisateur.
	 * @return L'utilisateur courant.
	 */
	public Utilisateur setPrenom(String prenom) { 
		this.prenom = prenom; 
		return this; 
	}

	/**
	 * Ajouter une discussion dans laquelle l'utilisateur se trouve.
	 * @param discussion Discussion à ajouter.
	 */
	public void addDiscussion(Discussion discussion) {
		this.discussions.add(discussion);
	}

	/**
	 * Retirer une discussion dans laquelle l'utilisateur se trouve.
	 * @param discussion Discussion à retirer.
	 */
	public void removeDiscussion(Discussion discussion) {
		this.discussions.remove(discussion);
	}

	/**
	 * Envoi d'un message à un utilisateur.
	 * @param message Message à envoyer
	 */
	public abstract void envoyerMessage(String message);

	@Override
	public int hashCode() {
		return (this.pseudonyme.hashCode() % 256) * 
				this.pseudonyme.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o.getClass() != this.getClass())
			return false;

		return ((Utilisateur)o).pseudonyme.equals(this.pseudonyme);
	}
}
