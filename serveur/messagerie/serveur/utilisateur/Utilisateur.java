package messagerie.serveur.utilisateur;

public class Utilisateur {
	private String pseudonyme;
	private String nom;
	private String prenom;

	public Utilisateur(String pseudonyme, String nom, String prenom) {
		this.pseudonyme = pseudonyme;
		this.nom = nom;
		this.prenom = prenom;
	}

	public String getPseudonyme() { return this.pseudonyme; }
	public String getNom() { return this.nom; }
	public String getPrenom() { return this.prenom; }

	public Utilisateur setNom(String nom) { 
		this.nom = nom; 
		return this; 
	}
	
	public Utilisateur setPrenom(String prenom) { 
		this.prenom = prenom; 
		return this; 
	}

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
