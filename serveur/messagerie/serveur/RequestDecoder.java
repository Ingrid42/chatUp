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

import messagerie.serveur.filtre.FiltreMot;

public class RequestDecoder {
	private final static Method[] methods;

	static {
		Class<RequestDecoder> requestDecoder = RequestDecoder.class;
		methods = requestDecoder.getMethods();
	}

	private Session session ;


	public RequestDecoder(Session session){
		this.session = session ;
	}

	public void decode(String json){
		JSONParser parser = new JSONParser() ;
		String parsingString = "{\"1\" : \"2\", \"3\" : 4}";

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

	public void connexion(JSONObject content) {
		try{
			String pseudo = (String)content.get("pseudonyme");
			String mdp = (String)content.get("mot_de_passe");
			UtilisateurHumain utilisateur = (UtilisateurHumain)Session.getApplication().getUtilisateur(pseudo);
			if(utilisateur.verifieMotDePasse(mdp)){
				this.session.setUtilisateur(utilisateur) ;
			}
			// TODO traitement si mot de passe eroné
		}
		catch (UtilisateurException ue) {
			System.err.println(ue.getMessage());
			// TODO Traitement pour renvoyer l'erreur au client si on ne trouve pas le client
		}
		catch (Exception pe) {
			pe.printStackTrace();
		}
	}

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

			// TODO Traitement pour renvoyer la confirmation au client (creer_utilisateur_reponse)
		}
		catch (UtilisateurException ue) {
			System.err.println(ue.getMessage());
			// TODO Traitement pour renvoyer l'erreur au client
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void creer_discussion(JSONObject content) {
		Discussion discussion = null;
		List<Utilisateur> utilisateurs = new ArrayList<>();

		try {
			JSONArray pseudonymes = (JSONArray)content.get("utilisateurs");
			for (Object p : pseudonymes)
				utilisateurs.add(Session.getApplication().getUtilisateur((String)p));

			discussion = new DiscussionTexte(utilisateurs);
			Session.getApplication().ajouterDiscussion(discussion);

			// TODO Traitement pour renvoyer la confirmation au client (creer_discussion_reponse)
		}
		catch (DiscussionException de) {
			System.err.println(de.getMessage());
			if (discussion != null) {
				for (Utilisateur u : utilisateurs)
					u.removeDiscussion(discussion);
			}
			// TODO Traitement pour renvoyer l'erreur au client
		}
		catch (UtilisateurException ue) {
			System.err.println(ue.getMessage());
			// TODO Traitement pour renvoyer l'erreur au client
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void envoyer_message(JSONObject content) {
		// on ne peut envoyer le msg que si la session a un utilisateur
		if (this.session.getUtilisateur() != null){
			try{
				int id = Integer.parseInt((String)content.get("id_discussion"));
				String texteMessage = (String)content.get("message");
				Message message = new Message(this.session.getUtilisateur(), texteMessage ) ;
				((DiscussionTexte)Session.getApplication().getDiscussion(id)).addMessage(message) ;
				// TODO traitement si message non envoyé
			} catch (Exception pe) {
				pe.printStackTrace();
			}
		}
		// TODO si celui qui envoie le message n'existe pas
	}

	public void get_utilisateurs(JSONObject content) {
	}

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

	public void get_profil(JSONObject content) {
	}

	public void add_filtre_mot(JSONObject content) {
		if (this.session.getUtilisateur() != null){
			try{
				this.session.getUtilisateur();
				String mdp = (String)content.get("mot_de_passe_parental");
				String mot = (String)content.get("mot");

				UtilisateurHumain utilisateur = (UtilisateurHumain)this.session.getUtilisateur() ;
				if (utilisateur.verifieMotDePasseParental(mdp)){
					utilisateur.ajouterFiltre(new FiltreMot(mot)) ;
					// TODO Traitement pour renvoyer la confirmation au client
				}
				// TODO Traitement si bad mdp parental
			} catch (Exception pe) {
				pe.printStackTrace();
			}
		}
		// TODO si celui qui envoie le message n'existe pas
	}

	public void add_filtre_utilisateur(JSONObject content) {
	}

	public void set_controle_parental(JSONObject content) {
	}

	public static void main(String[] args) {
		RequestDecoder rd = new RequestDecoder(null);
		rd.decode("{\"action\":\"connexion\", \"contenu\":{\"pseudonyme\" : \"TheLegend27\",\"mot_de_passe\" : \"motDePasse\"}}");
		rd.decode("{\"action\":\"creer\", \"contenu\":{\"pseudonyme\" : \"Orionis\",\"mot_de_passe\" : \"motDePasse\",\"nom\" : \"Zerhouni\",\"prenom\" : \"Youssef\",\"adresse_mel\" : \"youssef.zerhouni_abdou@insa-rouen.fr\",\"date_naissance\" : \"28/01/1996\"}}");
	}
}
