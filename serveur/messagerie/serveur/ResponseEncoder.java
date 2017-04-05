package messagerie.serveur;


import org.json.simple.JSONObject;
import messagerie.serveur.utilisateur.*;

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
		obj_etat.put("content", content);

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

  public String utilisateurs(){
    JSONObject obj = new JSONObject();
		JSONObject content = new JSONObject();
		obj.put("action", "utilisateurs") ;
		//content.put("utilisateurs", ) ;




		obj.put("contenu", content);
		return obj.toString() ;
  }

}
