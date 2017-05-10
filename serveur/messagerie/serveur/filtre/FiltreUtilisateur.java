package messagerie.serveur.filtre;

import messagerie.serveur.utilisateur.Utilisateur;

/**
 * Classe permettant de filtrer un utilisateur particulier.
 */
public class FiltreUtilisateur implements IFiltre {
	public final static long serialVersionUID = 147L;

	/**
	 * Utilisateur à filtrer.
	 */
	private Utilisateur utilisateur;

	/**
	 * Création d'un filtre.
	 * @param utilisateur Utilisateur à filtrer.
	 */
	public FiltreUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Override
	public Object getObject() { return this.utilisateur; }

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
