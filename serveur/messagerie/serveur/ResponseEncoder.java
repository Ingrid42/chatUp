package messagerie.serveur;

import java.util.Map;
import java.util.HashMap;
import java.util.Locale;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import messagerie.serveur.utilisateur.*;
import messagerie.serveur.discussion.*;

/**
 * Cette classe est chargée de gérer le formattage des réponses
 * envoyées par le serveur aux clients.
 */
public class ResponseEncoder {
	/**
	 * Session à laquelle les réponses doivent être envoyées.
	 */
	private Session session ;

	/**
	 * Crée un ReponseEncoder pour la session passée en paramètre.
	 * @param session Session du client qui devra recevoir les réponses liées à cet encodeur.
	 */
	public ResponseEncoder(Session session) {
		this.session = session ;
	}

	/**
	 * Lorsque le client a besoin d'un état pour la requête qu'il a
	 * transmise au serveur un réponse indiquant l'état de cette requête
	 * est envoyée au client.
	 * @param state Etat effectué ou non de la requête. Vrai indique une requête réussie.
	 * @param action Nom de la réponse pour que le client soit capable de détecter à quelle requête elle est associée.
	 * @return Objet JSON contenant les informations "action" et "etat"
	 */
	public Map<String, Object> stateReponse(boolean state, String action){
		Map<String, Object> jsonObjMap = new HashMap<>();
		jsonObjMap.put("action", action);
		jsonObjMap.put("etat", new Boolean(state));
		
		return jsonObjMap;
	}

	/**
	 * Le client peut avoir besoin des informations sur un utilisateur,
	 * cette méthode se charge donc de mettre en forme ces informations
	 * pour les envoyer.
	 * @param state Etat effectué ou non de la requête. Vrai indique une requête réussie.
	 * @param action Nom de la réponse pour que le client soit capable de détecter à quelle requête elle est associée.
	 * @param user Utilisateur qui doit être utilisé pour la réponse.
	 * @return Objet JSON représentant les informations de l'utilisateur.
	 */
	public JSONObject userStateReponse(boolean state, String action, UtilisateurHumain user){
		Map<String, Object> jsonObjEtatMap = stateReponse(state, action );
		Map<String, Object> jsonContentMap = new HashMap<>();

		// On ne met pas les informations du client dans la requête si elle a un état échoué.
		if (state) {
			jsonContentMap.put("pseudonyme", user.getPseudonyme());
			jsonContentMap.put("nom", user.getNom());
			jsonContentMap.put("prenom", user.getPrenom());
			jsonContentMap.put("adresse_mel", user.getAdresseMel());

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
			jsonContentMap.put("date_naissance", format.format(user.getDateNaissance()));
		}
		
		JSONObject content = new JSONObject(jsonContentMap);
		jsonObjEtatMap.put("contenu", content);
		JSONObject objEtat = new JSONObject(jsonObjEtatMap);
		
		return objEtat;
	}

	/**
	 * Réponse au client afin de l'informer si la connexion est acceptée ou non.
	 * @param state Un état vrai indique que la connexion a été acceptée.
	 * @return Réponse mise en forme au format JSON. Cette réponse contient les informations de l'utilisateur qui s'est connecté.
	 */
	public String connexionReponse(boolean state){
		return userStateReponse(state, "connexion_reponse", (UtilisateurHumain)this.session.getUtilisateur()).toString() ;
	}

	/**
	 * Réponse au client afin de l'informer si l'utilisateur a été créé ou non.
	 * @param state Un état vrai indique que la création a été effectuée.
	 * @param user Utilisateur créé.
	 * @return Réponse mise en forme au format JSON. Cette réponse contient les informations de l'utilisateur qui a été créé.
	 */
	public String creerUtilisateurReponse(boolean state, UtilisateurHumain user){
		return userStateReponse(state, "creer_utilisateur_reponse", user).toString() ;
	}

	/**
	 * Réponse au client afin de l'informer si la discussion a été créée.
	 * @param state Un état vrai indique que la création a été effectuée.
	 * @return Réponse mise en forme au format JSON.
	 */
	public String creerDiscussionReponse(boolean state){
		return new JSONObject(stateReponse(state, "creer_discussion_reponse" )).toString();
	}

	/**
	 * Réponse au client afin de l'informer si le message a bien été envoyé.
	 * @param state Un état vrai indique que le message a été envoyé.
	 * @return Réponse mise en forme au format JSON.
	 */
	public String envoyerMessageReponse(boolean state){
		return new JSONObject(stateReponse(state, "envoyer_message_reponse" )).toString();
	}
	
	/**
	 * Réponse au client afin de l'informer si la desactivation du controle parentale est faite.
	 * @param state Un état vrai indique que le message a été envoyé.
	 * @return Réponse mise en forme au format JSON.
	 */	
	public String desactiverControleParentalReponse(boolean state){
		return new JSONObject(stateReponse(state, "desactiver_controle_parental_reponse")).toString() ;
	}
	/**
	 * Réponse au client afin de l'informer si l'activation du controle parentale est faite.
	 * @param state Un état vrai indique que le message a été envoyé.
	 * @return Réponse mise en forme au format JSON.
	 */	
	public String setControleParentalReponse(boolean state){
		return new JSONObject(stateReponse(state, "set_controle_parental_reponse")).toString() ;
	}
	
	
	/**
	 * Réponse au client afin de l'informer si l'ajout du filtre a bien été effectué.
	 * @param state Un état vrai indique que le message a été envoyé.
	 * @return Réponse mise en forme au format JSON.
	 */	
	public String addFiltreUtilisateurReponse(boolean state){
		return new JSONObject(stateReponse(state, "add_filtre_utilisateur_reponse")).toString() ;
	}
	
	

	/**
	 * Encodage d'un message à un client.
	 * @param msg Message à encoder.
	 * @return Réponse mise en forme au format JSON.
	 */
	public String encoderMessage(Message msg){
		Map<String, Object> jsonObjMap = new HashMap<>();
		Map<String, Object> jsonContenuMap = new HashMap<>();
		
		jsonObjMap.put("action", "message") ;
		
		jsonContenuMap.put("id_discussion", msg.getId()) ;
		jsonContenuMap.put("pseudonyme", msg.getUtilisateur().getPseudonyme()) ;
		jsonContenuMap.put("message", msg.getMessage()) ;

		JSONObject contenu = new JSONObject(jsonContenuMap);
		jsonObjMap.put("contenu", contenu);

		JSONObject obj = new JSONObject();
		return obj.toString() ;
	}

	/**
	 * Liste tout les utilisateurs pour le client
	 * @return Réponse mise en forme au format JSON. Cette réponse contient une liste de tout les utilisateurs autorisés (N'étant pas filtrés).
	 */
	@SuppressWarnings("unchecked")
	public String getUtilisateursReponse(){
		Map<String, Object> jsonObjMap = new HashMap<>();
		Map<String, Object> jsonContenuMap = new HashMap<>();
		
		jsonObjMap.put("action", "get_utilisateurs_reponse") ;

		// TODO ne pas intégrer les utilisateurs qui sont filtrés (Contrôle parental)
		JSONArray array_users = new JSONArray();
		for(Utilisateur u : Session.getApplication().getUtilisateurs().values()){
			Map<String, Object> jsonUserObjectMap = new HashMap<>();

			jsonUserObjectMap.put("pseudonyme", u.getPseudonyme());
			jsonUserObjectMap.put("nom", u.getNom());
			jsonUserObjectMap.put("prenom", u.getPrenom());

			JSONObject userObject = new JSONObject();
			array_users.add(userObject) ;
		}

		jsonContenuMap.put("utilisateurs", array_users);
		JSONObject contenu = new JSONObject(jsonContenuMap);
		jsonObjMap.put("contenu", contenu);
		JSONObject obj = new JSONObject(jsonObjMap);

		return obj.toString() ;
	}
}
