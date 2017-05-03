package messagerie.serveur;


import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
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
	 * Crée un ResponseEncoder pour la session passée en paramètre.
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
	@SuppressWarnings("unchecked")
	public JSONObject stateResponse(boolean state, String action){
		JSONObject obj = new JSONObject();
		obj.put("action", action) ;
		obj.put("etat", new Boolean(state)) ;
		return obj ;
	}

	/**
	 * Le client peut avoir besoin des informations sur un utilisateur,
	 * cette méthode se charge donc de mettre en forme ces informations
	 * pour les envoyer.
	 * @param state Etat effectué ou non de la requête. Vrai indique une requête réussie.
	 * @param action Nom de la réponse pour que le client soit capable de détecter à quelle requête elle est associée.
	 * @return Objet JSON représentant les informations de l'utilisateur
	 */
	@SuppressWarnings("unchecked")
	public JSONObject userStateResponse(boolean state, String action){
		JSONObject obj_etat = stateResponse(state, action );
		JSONObject content = new JSONObject();

		// On ne met pas les informations du client dans la requête si elle a un état échoué.
		if (state) {
			UtilisateurHumain user = (UtilisateurHumain)this.session.getUtilisateur() ;
			content.put("pseudonyme", user.getPseudonyme());
			content.put("nom", user.getNom());
			content.put("prenom", user.getPrenom());
			content.put("adresse_mel", user.getAdresseMel());
			content.put("date_naissance", user.getDateNaissance());
		}

		obj_etat.put("contenu", content);
		
		return obj_etat ;
	}

	/**
	 * Réponse au client afin de l'informer si la connexion est acceptée ou non.
	 * @param state Un état vrai indique que la connexion a été acceptée.
	 * @return Réponse mise en forme au format JSON. Cette réponse contient les informations de l'utilisateur qui s'est connecté.
	 */
	public String connexionResponse(boolean state){
		return userStateResponse(state, "connexion_response").toString() ;
	}

	/**
	 * Réponse au client afin de l'informer si l'utilisateur a été créé ou non.
	 * @param state Un état vrai indique que la création a été effectuée.
	 * @return Réponse mise en forme au format JSON. Cette réponse contient les informations de l'utilisateur qui a été créé.
	 */
	public String creerUtilisateurResponse(boolean state){
		return userStateResponse(state, "connexion_response").toString() ;
	}

	/**
	 * Réponse au client afin de l'informer si la discussion a été créée.
	 * @param state Un état vrai indique que la création a été effectuée.
	 * @return Réponse mise en forme au format JSON.
	 */
	public String creerDiscussionResponse(boolean state){
		return stateResponse(state, "creer_discussion_response" ).toString();
	}

	/**
	 * Réponse au client afin de l'informer si le message a bien été envoyé.
	 * @param state Un état vrai indique que le message a été envoyé.
	 * @return Réponse mise en forme au format JSON.
	 */
	public String envoyerMessageResponse(boolean state){
		return stateResponse(state, "envoyer_message_response" ).toString();
	}

	/**
	 * Encodage d'un message à un client.
	 * @param msg Message à encoder.
	 * @return Réponse mise en forme au format JSON.
	 */
	@SuppressWarnings("unchecked")
	public String encoderMessage(Message msg){
		JSONObject obj = new JSONObject();
		obj.put("action", "message") ;
		JSONObject content = new JSONObject();
		content.put("id_discussion", msg.getId()) ;
		content.put("pseudonyme", msg.getUtilisateur().getPseudonyme()) ;
		content.put("message", msg.getMessage()) ;
		obj.put("contenu", content);
		return obj.toString() ;
	}

	/**
	 * Liste tout les utilisateurs pour le client
	 * @return Réponse mise en forme au format JSON. Cette réponse contient une liste de tout les utilisateurs autorisés (N'étant pas filtrés).
	 */
	@SuppressWarnings("unchecked")
	public String getUtilisateursResponse(){
		JSONObject obj = new JSONObject();
		obj.put("action", "utilisateurs") ;

		// TODO ne pas intégrer les utilisateurs qui sont filtrés (Contrôle parental)
		JSONArray array_users = new JSONArray();
		for(Utilisateur u : Session.getApplication().getUtilisateurs().values()){
			JSONObject userObject = new JSONObject();
			userObject.put("pseudonyme", u.getPseudonyme());
			userObject.put("nom", u.getNom());
			userObject.put("prenom", u.getPrenom());

			array_users.add(userObject) ;
		}
		JSONObject content = new JSONObject();
		content.put("utilisateurs", array_users);
		obj.put("contenu", content);

		return obj.toString() ;
	}
}
