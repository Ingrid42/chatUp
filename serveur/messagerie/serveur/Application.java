package messagerie.serveur;

import messagerie.serveur.utilisateur.Utilisateur;
import messagerie.serveur.exception.*;

import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

public class Application implements Serializable {
	public final static long serialVersionUID = 145L;

	private static Application application;
	private Map<String, Utilisateur> utilisateurs;
	
	private Application() {
		this.utilisateurs = new HashMap<String, Utilisateur>();
		Application.application = this;
	}

	public synchronized void ajouterUtilisateur(Utilisateur utilisateur) throws UtilisateurException {
		if (this.utilisateurs.containsKey(utilisateur.getPseudonyme()))
			throw new UtilisateurException("Utilisateur existant.");
		this.utilisateurs.put(utilisateur.getPseudonyme(), utilisateur);
	}

	public synchronized Utilisateur getUtilisateur(String pseudonyme) throws UtilisateurException {
		Utilisateur utilisateur = this.utilisateurs.get(pseudonyme);
		if (utilisateur == null)
			throw new UtilisateurException(String.format("L'utilisateur '%s' n'existe pas.", pseudonyme));
		return utilisateur;
	}

	public static Application getInstance() {
		if (Application.application == null)
			return new Application();
		return Application.application;
	}
}