package messagerie.serveur.utilisateur;

import messagerie.serveur.filtre.*;
import messagerie.serveur.discussion.Message;
import messagerie.serveur.Session;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collections;

import java.io.IOException;

/**
 * Classe représentant un utilisateur de l'application.
 */
public class UtilisateurHumain extends Utilisateur {
	public final static long serialVersionUID = 2547L;

	/**
	 * URL vers la photo par défaut de l'utilisateur.
	 */
	public final static String photoDefaut = "defaut.png";

	/**
	 * Mot de passe de l'utilisateur.
	 */
	private String motDePasse;

	/**
	 * Adresse mel de l'utilisateur.
	 */
	private String adresseMel;

	/**
	 * Date de naissance de l'utilisateur.'
	 */
	private Date dateNaissance;

	/**
	 * Mot de passe du contrôle parental.
	 */
	private String motDePasseParental;

	/**
	 * Etat du mot de passe parental (activé ou non).
	 */
	private boolean etatMotDePasseParental;

	/**
	 * URL vers la photo de l'utilisateur.
	 */
	private String photo;

	/**
	 * Session de l'utilisateur.
	 */
	private transient Session session;

	/**
	 * Liste des messages reçus hors connexion
	 */
	 private List<String> messages;

	/**
	 * Filtres associés au contrôle parental.
	 */
	private Set<IFiltre> filtres;

	/**
	 * Création d'un utilisateur de l'application.
	 * @param pseudonyme Pseudonyme de l'utilisateur.
	 * @param nom Nom de l'utilisateur.
	 * @param prenom Prénom de l'utilisateur.
	 * @param motDePasse Mot de passe de l'utilisateur.
	 * @param adresseMel Adresse mel de l'utilisateur.
	 * @param dateNaissance Date de naissance de l'utilisateur.
	 */
	public UtilisateurHumain(String pseudonyme, String nom, String prenom,
							 String motDePasse, String adresseMel,
							 Date dateNaissance) {
		super(pseudonyme, nom, prenom);

		this.motDePasse = motDePasse;
		this.etatMotDePasseParental = false;
		this.adresseMel = adresseMel;
		this.dateNaissance = dateNaissance;

		this.motDePasseParental = null;
		this.photo = UtilisateurHumain.photoDefaut;

		this.session = null;
		this.messages = new ArrayList<>();

		this.filtres = new HashSet<IFiltre>();
	}

	/**
	 * Changer de mot de passe.
	 * @param motDePasse Nouveau mot de passe.
	 * @return L'utilisateur courant.
	 */
	public UtilisateurHumain setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
		return this;
	}

	/**
	 * Changer d'adresse mel.
	 * @param adresseMel Nouvelle adresse mel.
	 * @return L'utilisateur courant.
	 */
	public UtilisateurHumain setAdresseMel(String adresseMel) {
		this.adresseMel = adresseMel;
		return this;
	}
	
	
	/**
	 * retourne letat du controle parental
	 * @return letat du controle parental.
	 */
	public boolean getControleParental() {
		return this.etatMotDePasseParental;
	}

	/**
	 * Changer de date de naissance.
	 * @param dateNaissance Nouvelle date de naissance.
	 * @return L'utilisateur courant.
	 */
	public UtilisateurHumain setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
		return this;
	}

	/**
	 * Définir le mot de passe pour le contrôle parental.
	 * @param motDePasseParental Mot de passe du contrôle parental.
	 * @return L'utilisateur courant.
	 */
	public UtilisateurHumain setMotDePasseParental(boolean etat, String motDePasseParental) {
		this.etatMotDePasseParental = etat;
		this.motDePasseParental = motDePasseParental;

		if (motDePasseParental == null)
			this.filtres.clear();

		return this;
	}

	/**
	 * Définir la photo de l'utilisateur.
	 * @param photo URL vers la photo de l'utilisateur.
	 * @return L'utilisateur courant.
	 */
	public UtilisateurHumain setPhoto(String photo) {
		this.photo = photo;
		return this;
	}

	/**
	 * Définir la session de l'utilisateur.
	 * @param session Session active.
	 * @return L'utilisateur courant.
	 */
	public UtilisateurHumain setSession(Session session) {
		this.session = session;
		return this;
	}

	/**
	 * Vérifier l'exactitude du mot de passe avec la chaine passée en paramètre.
	 * @param motDePasse Chaine à vérifier.
	 * @return Vrai si la chaine passée en paramètre correspond au mot de passe. Faux sinon.
	 */
	public boolean verifieMotDePasse(String motDePasse) {
		return motDePasse.equals(this.motDePasse);
	}

	/**
	 * Vérifier l'exactitude du mot de passe du contrôle parental avec la chaine passée en paramètre.
	 * @param motDePasseParental Chaine à vérifier.
	 * @return Vrai si la chaine passée en paramètre correspond au mot de passe du contrôle parental. Faux sinon.
	 */
	public boolean verifieMotDePasseParental(String motDePasseParental) {
		if (this.motDePasseParental == null)
			return (motDePasseParental == null ? true : false);
		return motDePasseParental.equals(this.motDePasseParental);
	}

	/**
	 * Récupérer l'adresse mel de l'utilisateur.
	 * @return Adresse mel de l'utilisateur.
	 */
	public String getAdresseMel() { return this.adresseMel; }

	/**
	 * Récupérer la photo de l'utilisateur.
	 * @return Photo de l'utilisateur.
	 */
	public String getPhoto() { return this.photo; }

	/**
	 * Récupérer la date de naissance de l'utilisateur.
	 * @return Date de naissance de l'utilisateur.
	 */
	public Date getDateNaissance() { return this.dateNaissance; }

	/**
	 * Récupérer la session active de l'utilisateur.
	 * @return Session de l'utilisateur.
	 */
	public Session getSession() { return this.session; }

	/**
	 * Ajouter un filtre au contrôle parental.
	 * @param filtre Filtre à ajouter.
	 * @return Vrai si le filtre a été ajouté. Faux sinon.
	 */
	public boolean ajouterFiltre(IFiltre filtre) {
		if (this.filtres.contains(filtre))
			return false;

		if (!this.etatMotDePasseParental)
			return false;

		return this.filtres.add(filtre);
	}

	/**
	 * Retirer un filtre au contrôle parental.
	 * @param filtre Filtre à retirer.
	 * @return Vrai si le filtre a été retiré. Faux sinon.
	 */
	public boolean retirerFiltre(IFiltre filtre) {
		return this.filtres.remove(filtre);
	}

	/**
	 * Permet de retirer les mots filtrés d'un message.
	 * @param message Message à filtrer
	 * @return Chaine représentant le mot filtré
	 */
	public String filtrerMessage(Message message) {
		String messageString = message.getMessage();

		for (IFiltre filtre : filtres) {
			if (!(filtre instanceof FiltreMot))
				continue;
			
			String mot = (String)filtre.getObject();
			messageString = messageString.replaceAll("(?i)" + mot, String.join("", Collections.nCopies(mot.length(), "*")));
		}

		return messageString;
	}

	/**
	 * Vérifie si l'utilisateur peut avoir accès à l'information passée en paramètre.
	 * @param objct Objet à vérifier.
	 * @return Vrai si l'objet n'est pas filtré. Faux sinon.
	 */
	public boolean peutVoir(Object object) {
		for (IFiltre f : filtres) {
			if (f.compare(object))
				return false;
		}

		return true;
	}

	/**
	 * Envoi d'un message à un utilisateur.
	 * @param message Message à envoyer
	 */
	public void envoyerMessage(String message) {
		if (this.session != null) {
			try {
				this.session.envoyerMessage(message);
			}
			catch (IOException ioe) {
				System.err.println("Impossible d'envoyer le message. Envoi différé.");
				this.messages.add(message);
			}
		}
		else {
			this.messages.add(message);
		}
	}
	
	public Set<IFiltre> getFiltres(){
		return this.filtres ;
	}
}
