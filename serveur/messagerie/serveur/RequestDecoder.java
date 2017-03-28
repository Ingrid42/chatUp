package messagerie.serveur;

import messagerie.serveur.utilisateur.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.lang.reflect.Method;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
		System.out.println("CONNEXION");
	}
	
	public void creer(JSONObject content) throws ParseException  {
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
		}catch (Exception pe) {
			pe.printStackTrace();
		}

	}


	public static void main(String[] args) {
		RequestDecoder rd = new RequestDecoder(null);
		rd.decode("{\"action\":\"connexion\", \"contenu\":{\"pseudonyme\" : \"TheLegend27\",\"mot_de_passe\" : \"motDePasse\"}}");
		rd.decode("{\"action\":\"creer\", \"contenu\":{\"pseudonyme\" : \"Orionis\",\"mot_de_passe\" : \"motDePasse\",\"nom\" : \"Zerhouni\",\"prenom\" : \"Youssef\",\"adresse_mel\" : \"youssef.zerhouni_abdou@insa-rouen.fr\",\"date_naissance\" : \"28/01/1996\"}}");
	}
}