package messagerie.serveur.filtre;

import messagerie.serveur.utilisateur.Utilisateur;

public class FiltreUtilisateur implements IFiltre {
	private Utilisateur utilisateur;

	public FiltreUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Override
	public boolean compare(Object objet) {
		if (objet instanceof Utilisateur)
			return ((Utilisateur)objet).equals(this.utilisateur);
		return false;
	}
}
