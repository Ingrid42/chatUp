package messagerie.serveur;

import messagerie.serveur.utilisateur.Utilisateur;
import messagerie.serveur.discussion.*;
import messagerie.serveur.exception.*;

import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

/**
 * Classe servant au regroupement des données du serveur.
 * Elle a pour objectif de fournir les méthodes nécessaires
 * à la récupération des données.
 * Afin de s'assurer qu'un seul objet est en charge des données,
 * cette classe ne peut être instanciée qu'une seule fois.
 */
public class Application implements Serializable {
	public final static long serialVersionUID = 145L;

	/**
	 * Instance unique de l'application.
	 */
	private static Application application;

	/**
	 * Liste des utilisateurs de l'application.
	 */
	private Map<String, Utilisateur> utilisateurs;

	/**
	 * Liste des discussions existantes.
	 */
	private Map<Long, Discussion> discussions;

	/**
	 * Création d'un objet application. Ce constructeur ne doit en aucun cas être appelé,
	 * seul la méthode getInstance() doit permettre de récupérer un objet Application.
	 */
	private Application() {
		this.utilisateurs = new HashMap<String, Utilisateur>();
		this.discussions = new HashMap<Long, Discussion>();
		Application.application = this;
	}

	/**
	 * Ajouter un utilisateurs à la liste des utilisateurs.
	 * @param utilisateur Utilisateur à ajouter.
	 * @throws UtilisateurException Se déclenche si un utilisateur avec le même pseudonyme existe déjà.
	 */
	public synchronized void ajouterUtilisateur(Utilisateur utilisateur) throws UtilisateurException {
		if (this.utilisateurs.containsKey(utilisateur.getPseudonyme()))
			throw new UtilisateurException("Utilisateur existant.");
		this.utilisateurs.put(utilisateur.getPseudonyme(), utilisateur);
	}

	/**
	 * Récupérer un utilisateur à l'aide de son pseudonyme.
	 * @param pseudonyme Pseudonyme de l'utilisateur à rechercher.
	 * @throws UtilisateurException Se déclenche si l'utilisateur n'existe pas.
	 * @return Utilisateur ayant le pseudonyme voulu.
	 */
	public Utilisateur getUtilisateur(String pseudonyme) throws UtilisateurException {
		Utilisateur utilisateur = this.utilisateurs.get(pseudonyme);
		if (utilisateur == null)
			throw new UtilisateurException(String.format("L'utilisateur '%s' n'existe pas.", pseudonyme));
		return utilisateur;
	}

	/**
	 * Ajouter une discussion à la liste des discussions.
	 * @param discussion Discussion à ajouter.
	 * @throws DiscussionException Se déclenche si une discussion ayant le même identifiant existe déjà.
	 */
	public synchronized void ajouterDiscussion(Discussion discussion) throws DiscussionException {
		if (this.discussions.containsKey(discussion.getId()))
			throw new DiscussionException("Discussion existante.");
		this.discussions.put(discussion.getId(), discussion);
	}

	/**
	 * Récuperer une discussion à l'aide de son identifiant.
	 * @param id Identifiant de la discussion.
	 * @throws DiscussionException Se déclenche si la discussion souhaitée n'existe pas.
	 * @return Discussion ayant l'id voulu.
	 */
	public Discussion getDiscussion(long id) throws DiscussionException {
		Discussion discussion = this.discussions.get(id);
		if (discussion == null)
			throw new DiscussionException(String.format("La discussion ayant pour id %l n'existe pas.", id));
		return discussion;
	}

	/**
	 * Récupérer la liste complète des utilisateurs existants.
	 * @return Liste des utilisateurs existants.
	 */
	public Map<String, Utilisateur> getUtilisateurs(){
		return this.utilisateurs ;
	}

	/**
	 * Récupérer une instance de la classe Application.
	 * @return Instance unique d'Application.
	 */
	public static Application getInstance() {
		if (Application.application == null)
			return new Application();
		return Application.application;
	}
}
