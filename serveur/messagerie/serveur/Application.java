package messagerie.serveur;

import messagerie.serveur.utilisateur.Utilisateur;
import messagerie.serveur.discussion.*;
import messagerie.serveur.exception.*;

import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

public class Application implements Serializable {
	public final static long serialVersionUID = 145L;

	private static Application application;
	private Map<String, Utilisateur> utilisateurs;
	private Map<Long, Discussion> discussions;

	private Application() {
		this.utilisateurs = new HashMap<String, Utilisateur>();
		this.discussions = new HashMap<Long, Discussion>();
		Application.application = this;
	}

	public synchronized void ajouterUtilisateur(Utilisateur utilisateur) throws UtilisateurException {
		if (this.utilisateurs.containsKey(utilisateur.getPseudonyme()))
			throw new UtilisateurException("Utilisateur existant.");
		this.utilisateurs.put(utilisateur.getPseudonyme(), utilisateur);
	}

	public Utilisateur getUtilisateur(String pseudonyme) throws UtilisateurException {
		Utilisateur utilisateur = this.utilisateurs.get(pseudonyme);
		if (utilisateur == null)
			throw new UtilisateurException(String.format("L'utilisateur '%s' n'existe pas.", pseudonyme));
		return utilisateur;
	}

	public synchronized void ajouterDiscussion(Discussion discussion) throws DiscussionException {
		if (this.discussions.containsKey(discussion.getId()))
			throw new DiscussionException("Discussion existante.");
		this.discussions.put(discussion.getId(), discussion);
	}

	public Discussion getDiscussion(long id) throws DiscussionException {
		Discussion discussion = this.discussions.get(id);
		if (discussion == null)
			throw new DiscussionException(String.format("La discussion ayant pour id %l n'existe pas.", id));
		return discussion;
	}
	public Map<String, Utilisateur> getUtilisateurs(){
		return this.utilisateurs ;
	}

	public static Application getInstance() {
		if (Application.application == null)
			return new Application();
		return Application.application;
	}
}
