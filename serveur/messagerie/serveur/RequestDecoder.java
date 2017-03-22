package messagerie.serveur;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.lang.reflect.Method;

public class RequestDecoder {

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
			// introspection get les methodes et les a&ppele
		}
		catch (ParseException pe) {
			pe.printStackTrace();
		}
	}
}