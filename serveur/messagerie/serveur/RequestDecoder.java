package messagerie.serveur;

import messagerie.serveur.utilisateur.*;
import messagerie.serveur.discussion.*;
import messagerie.serveur.exception.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.lang.reflect.Method;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import java.util.List;
import java.util.ArrayList;

import java.io.IOException;

import messagerie.serveur.filtre.*;

/**
 * Cette classe est chargée de décoder les requêtes
 * envoyées par le client au seveur.
 */
public class RequestDecoder {
	/**
	 * Méthodes de la classe pour simplifier l'appel aux différentes méthodes selon l'action 
	 * indiquée dans la requête.
	 */
	private final static Method[] methods;

	static {
		Class<RequestDecoder> requestDecoder = RequestDecoder.class;
		methods = requestDecoder.getMethods();
	}

	/**
	 * Session d'où provient la requête
	 */
	private Session session ;

	/**
	 * Encodeur pour les réponses.
	 */
	private ResponseEncoder encodeur;

	/**
	 * Crée un objet RequestDecoder permettant de décoder des requêtes JSON.
	 * @param session Session d'où provient les requêtes.
	 */
	public RequestDecoder(Session session){
		this.session = session ;
		this.encodeur = new ResponseEncoder(session);
	}

	/**
	 * Transforme une chaine de caractère en objet JSON et appelle l'action associée.
	 * @param json Chaine à décoder
	 */
	public void decode(String json){
		JSONParser parser = new JSONParser() ;

		try{
			JSONObject obj =  (JSONObject)parser.parse(json);
			String action = obj.get("action").toString();
			JSONObject content = (JSONObject)obj.get("contenu");

			Method requested = RequestDecoder.class.getMethod(action, JSONObject.class);

			requested.invoke(this, content);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Connexion d'un utilisateur.
	 * @param content Requête reçue par le serveur.
	 */
	public void connexion(JSONObject content) {
		try{
			String pseudo = (String)content.get("pseudonyme");
			String mdp = (String)content.get("mot_de_passe");
			UtilisateurHumain utilisateur = (UtilisateurHumain)Session.getApplication().getUtilisateur(pseudo);
			if(utilisateur.verifieMotDePasse(mdp)){
				this.session.setUtilisateur(utilisateur);
				utilisateur.setSession(this.session);
				this.session.envoyerMessage(
					this.encodeur.connexionResponse(true)
				);
			}
			else 
				this.session.envoyerMessage(
					this.encodeur.connexionResponse(false)
				);
		}
		catch (UtilisateurException ue) {
			System.err.println(ue.getMessage());

			try {
				this.session.envoyerMessage(
					this.encodeur.connexionResponse(false)
				);
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
		}
		catch (Exception pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * Création d'un utilisateur.
	 * @param content Requête reçue par le serveur.
	 */
	public void creer_utilisateur(JSONObject content) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
		try{
			Session.getApplication().ajouterUtilisateur(new UtilisateurHumain(
				(String)content.get("pseudonyme"),
				(String)content.get("nom"),
				(String)content.get("prenom"),
				(String)content.get("mot_de_passe"),
				(String)content.get("adresse_mel"),
				format.parse((String)content.get("date_naissance"))
			));

			this.session.envoyerMessage(
				this.encodeur.creerUtilisateurResponse(true)
			);
		}
		catch (UtilisateurException ue) {
			System.err.println(ue.getMessage());

			try {
				this.session.envoyerMessage(
					this.encodeur.creerUtilisateurResponse(false)
				);
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Création d'une discussion.
	 * @param content Requête reçue par le serveur.
	 */
	public void creer_discussion(JSONObject content) {
		Discussion discussion = null;
		List<Utilisateur> utilisateurs = new ArrayList<>();

		try {
			JSONArray pseudonymes = (JSONArray)content.get("utilisateurs");
			for (Object p : pseudonymes)
				utilisateurs.add(Session.getApplication().getUtilisateur((String)p));

			discussion = new DiscussionTexte(utilisateurs);
			Session.getApplication().ajouterDiscussion(discussion);
			this.session.envoyerMessage(
				this.encodeur.creerDiscussionResponse(true)
			);
		}
		catch (DiscussionException de) {
			System.err.println(de.getMessage());
			if (discussion != null) {
				for (Utilisateur u : utilisateurs)
					u.removeDiscussion(discussion);
			}

			try {
				this.session.envoyerMessage(
					this.encodeur.creerDiscussionResponse(false)
				);
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		catch (UtilisateurException ue) {
			System.err.println(ue.getMessage());

			try {
				this.session.envoyerMessage(
					this.encodeur.creerDiscussionResponse(false)
				);
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Envoi d'un message aux utilisateurs d'une discussion.
	 * @param content Requête reçue par le serveur.
	 */
	public void envoyer_message(JSONObject content) {
		// On ne peut envoyer le msg que si la session a un utilisateur
		if (this.session.getUtilisateur() != null){
			try{
				int id = Integer.parseInt((String)content.get("id_discussion"));
				String texteMessage = (String)content.get("message");
				Message message = new Message(this.session.getUtilisateur(), texteMessage, id) ;

				DiscussionTexte discussion = ((DiscussionTexte)Session.getApplication().getDiscussion(id));
				if (discussion.possedeUtilisateur(this.session.getUtilisateur()))
					discussion.addMessage(message);
				else
					throw new DiscussionException("L'utilisateur n'est pas dans la conversation. Impossible d'envoyer un message.");

				
				// TODO envoi de l'etat
				// TODO envoi de message aux utilisateurs
				for (Utilisateur u : discussion.getUtilisateurs())
					if (!u.equals(discussion.getUtilisateurs()))
						u.envoyerMessage(this.encodeur.encoderMessage(message));
			}
			catch(DiscussionException de) {
				System.err.println(de.getMessage());

				try {
					this.session.envoyerMessage(
						this.encodeur.envoyerMessageResponse(false)
					);
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}
			} 
			catch (Exception pe) {
				pe.printStackTrace();
			}
		}
		// TODO si celui qui envoie le message n'existe pas
	}

	/**
	 * Envoi de la liste des utilisateurs au client.
	 * @param content Requête reçue par le serveur.
	 */
	public void get_utilisateurs(JSONObject content) {
		try {
			this.session.envoyerMessage(
				this.encodeur.getUtilisateursResponse()
			);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Modification du profil d'un utilisateur.
	 * @param content Requête reçue par le serveur.
	 */
	public void modifier_profil(JSONObject content) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);

		try {
			Utilisateur utilisateur = this.session.getUtilisateur();
			if (!(utilisateur instanceof UtilisateurHumain))
				throw new UtilisateurException("Seul un UtilisateurHumain peut être modifié.");

			UtilisateurHumain utilHumain = (UtilisateurHumain)utilisateur;
			utilHumain.setMotDePasse((String)content.get("mot_de_passe"))
					  .setAdresseMel((String)content.get("adresse_mel"))
					  .setDateNaissance(format.parse((String)content.get("date_naissance")))
					  .setNom((String)content.get("nom"))
					  .setPrenom((String)content.get("prenom"));

			// TODO Traitement pour renvoyer la confirmation au client (modifier_profil_reponse)
		}
		catch (UtilisateurException ue) {
			System.err.println(ue.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Envoi du profil d'un utilisateur.
	 * @param content Requête reçue par le serveur.
	 */
	public void get_profil(JSONObject content) {
	}

	/**
	 * Filtrer un mot pour l'utilisateur correspondant à la session active.
	 * @param content Requête reçue par le serveur.
	 */
	public void add_filtre_mot(JSONObject content) {
		if (this.session.getUtilisateur() != null){
			try{
				String mdp = (String)content.get("mot_de_passe_parental");
				String mot = (String)content.get("mot");

				UtilisateurHumain utilisateur = (UtilisateurHumain)this.session.getUtilisateur();
				if (utilisateur.verifieMotDePasseParental(mdp)){
					utilisateur.ajouterFiltre(new FiltreMot(mot));
					// TODO Traitement pour renvoyer la confirmation au client
				}
				// TODO else: Traitement si bad mdp parental
			} catch (Exception pe) {
				pe.printStackTrace();
			}
		}
		// TODO si celui qui envoie le message n'existe pas
	}

	/**
	 * Filtrer un utilisateur pour l'utilisateur correspondant à la session active.
	 * @param content Requête reçue par le serveur.
	 */
	public void add_filtre_utilisateur(JSONObject content) {
		try {
			String mdp = (String)content.get("mot_de_passe_parental");
			String nom = (String)content.get("mot");

			UtilisateurHumain utilisateur = (UtilisateurHumain)this.session.getUtilisateur();
			if (utilisateur.verifieMotDePasseParental(mdp)){
				Utilisateur utilisateurBanni = Session.getApplication().getUtilisateur(nom);
				utilisateur.ajouterFiltre(new FiltreUtilisateur(utilisateurBanni));
				// TODO Traitement pour renvoyer la confirmation au client
			}
			// TODO else: Traitement si bad mdp parental
		}
		catch (Exception pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * Activer le contrôle parental.
	 * @param content Requête reçue par le serveur.
	 */
	public void set_controle_parental(JSONObject content) {
		try {
			String mdp = (String)content.get("mot_de_passe_parental");

			UtilisateurHumain utilisateur = (UtilisateurHumain)this.session.getUtilisateur();
			if (utilisateur.verifieMotDePasseParental(null)) {
				utilisateur.setMotDePasseParental(mdp);
				// TODO envoyer la confirmation
			}
			// TODO else: Renvoyer une erreur
			
		}
		catch (Exception pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * Désactiver le contrôle parental.
	 * @param content Requête reçue par le serveur.
	 */
	public void desactiver_controle_parental(JSONObject content) {
		try {
			String mdp = (String)content.get("mot_de_passe_parental");

			UtilisateurHumain utilisateur = (UtilisateurHumain)this.session.getUtilisateur();
			if (utilisateur.verifieMotDePasseParental(mdp)){
				utilisateur.setMotDePasseParental(null);
				try {
					this.session.envoyerMessage(
						this.encodeur.connexionResponse(true)
					);
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			// TODO else: Renvoyer une erreur
		}
		catch (Exception pe) {
			pe.printStackTrace();
		}
	}

	public static void main(String[] args) {
		RequestDecoder rd = new RequestDecoder(null);
		rd.decode("{\"action\":\"connexion\", \"contenu\":{\"pseudonyme\" : \"TheLegend27\",\"mot_de_passe\" : \"motDePasse\"}}");
		rd.decode("{\"action\":\"creer\", \"contenu\":{\"pseudonyme\" : \"Orionis\",\"mot_de_passe\" : \"motDePasse\",\"nom\" : \"Zerhouni\",\"prenom\" : \"Youssef\",\"adresse_mel\" : \"youssef.zerhouni_abdou@insa-rouen.fr\",\"date_naissance\" : \"28/01/1996\"}}");
	}
}
