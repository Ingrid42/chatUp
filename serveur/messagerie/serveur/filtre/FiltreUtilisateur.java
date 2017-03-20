package messagerie.serveur.filtre;

import messagerie.serveur.utilisateur.Utilisateur;

public class FiltreUtilisateur implements IFiltre {
	public final static long serialVersionUID = 147L;

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

	@Override
	public boolean equals(Object objet) {
		if (objet == this)
			return true;

		if (objet.getClass() != this.getClass())
			return false;

		return ((FiltreUtilisateur)objet).utilisateur.equals(this.utilisateur);
	}

	@Override
	public int hashCode() {
		return (this.utilisateur.hashCode() % 512) * 
				this.utilisateur.hashCode();
	}

}
