package messagerie.serveur.utilisateur;

import messagerie.serveur.discussion.*;

import java.util.List;
import java.util.ArrayList;

import java.io.Serializable;

public class Utilisateur implements Serializable {
	public final static long serialVersionUID = 951L;

	private String pseudonyme;
	private String nom;
	private String prenom;

	private List<Discussion> discussions;

	public Utilisateur(String pseudonyme, String nom, String prenom) {
		this.pseudonyme = pseudonyme;
		this.nom = nom;
		this.prenom = prenom;

		this.discussions = new ArrayList<Discussion>();
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

	public void addDiscussion(Discussion discussion) {
		this.discussions.add(discussion);
	}

	public void removeDiscussion(Discussion discussion) {
		this.discussions.remove(discussion);
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
