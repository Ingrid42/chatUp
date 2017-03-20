package messagerie.serveur;

import messagerie.serveur.utilisateur.Utilisateur;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class Application implements Serializable {
	public final static long serialVersionUID = 145L;

	private static Application application;
	private List<Utilisateur> utilisateurs;
	
	private Application() {
		this.utilisateurs = new ArrayList<Utilisateur>();
		Application.application = this;
	}

	public void ajouterUtilisateur(Utilisateur utilisateur) {
		this.utilisateurs.add(utilisateur);
	}

	public static Application getInstance() {
		if (Application.application == null)
			return new Application();
		return Application.application;
	}
}