package messagerie.serveur;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.lang.reflect.Method;

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
		catch (ParseException pe) {
			pe.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connexion(JSONObject content) {
		System.out.println("CONNEXION");
	}
	
	public void creer(JSONObject content) {
		System.out.println("CREER");
	}


	public static void main(String[] args) {
		RequestDecoder rd = new RequestDecoder(null);
		rd.decode("{\"action\":\"connexion\", \"contenu\":{\"pseudonyme\" : \"TheLegend27\",\"mot_de_passe\" : \"motDePasse\"}}");
		rd.decode("{\"action\":\"creer\", \"contenu\":{\"pseudonyme\" : \"Orionis\",\"mot_de_passe\" : \"motDePasse\",\"nom\" : \"Zerhouni\",\"prenom\" : \"Youssef\",\"adresse_mel\" : \"youssef.zerhouni_abdou@insa-rouen.fr\",\"date_naissance\" : \"28/01/1996\"}}");
	}
}