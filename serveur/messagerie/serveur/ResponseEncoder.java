package messagerie.serveur;
 

import org.json.simple.JSONObject;

public class ResponseEncoder {
	private Session session ;
	
	public ResponseEncoder(Session session) {
		this.session = session ;
	}
	
	public String state_response(int state, String action){
		JSONObject obj = new JSONObject();
		JSONObject content = new JSONObject();
		obj.put("action", action) ;
		content.put("etat", new Integer(state)) ;
		obj.put("contenu", content);
		return obj.toString() ;
	}
	
	public String connexion_response(int state){
		return state_response(state, "connexion_response" );
	}
	
	public String creer_utilisateur_response(int state){
		return state_response(state, "creer_utilisateur_response" );
	}
	
	public String creer_discussion_response(int state){
		return state_response(state, "creer_discussion_response" );
	}
	
	public String envoyer_message_response(int state){
		return state_response(state, "envoyer_message_response" );
	}
	
	
}