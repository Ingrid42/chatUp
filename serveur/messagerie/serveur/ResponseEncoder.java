package messagerie.serveur;


import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import messagerie.serveur.utilisateur.*;
import messagerie.serveur.discussion.*;

public class ResponseEncoder {
	private Session session ;

	public ResponseEncoder(Session session) {
		this.session = session ;
	}

	public JSONObject state_response(boolean state, String action){
		JSONObject obj = new JSONObject();
		obj.put("action", action) ;
		obj.put("etat", new Boolean(state)) ;
		return obj ;
	}
	public JSONObject user_state_response(boolean state, String action){
		JSONObject obj_etat = state_response(state, action );
    UtilisateurHumain user = (UtilisateurHumain)this.session.getUtilisateur() ;
		JSONObject content = new JSONObject();
		content.put("pseudonyme", user.getPseudonyme());
		content.put("nom", user.getNom());
		content.put("prenom", user.getPrenom());
		content.put("adresse_mel", user.getAdresseMel());
		content.put("date_naissance", user.getDateNaissance());
		obj_etat.put("contenu", content);

		return obj_etat ;
	}

	public String connexion_response(boolean state){
		return user_state_response(state, "connexion_response").toString() ;

	}

	public String creer_utilisateur_response(boolean state){
		return user_state_response(state, "connexion_response").toString() ;
	}

	public String creer_discussion_response(boolean state){
		return state_response(state, "creer_discussion_response" ).toString();
	}

	public String envoyer_message_response(boolean state){
		return state_response(state, "envoyer_message_response" ).toString();
	}


	public String envoyer_message(Message msg){
		JSONObject obj = new JSONObject();
		obj.put("action", "message") ;
		JSONObject content = new JSONObject();
		content.put("id_discussion", msg.getId()) ;
		content.put("pseudonyme", msg.getUtilisateur().getPseudonyme()) ;
		content.put("message", msg.getMessage()) ;
		obj.put("contenu", content);
		return obj.toString() ;
	}

	public String utilisateurs(){
		JSONObject obj = new JSONObject();
		obj.put("action", "utilisateurs") ;

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
