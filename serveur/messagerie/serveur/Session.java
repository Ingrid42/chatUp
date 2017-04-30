package messagerie.serveur;

import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import messagerie.serveur.RequestDecoder;
import messagerie.serveur.utilisateur.*;

import javax.websocket.Endpoint;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.RemoteEndpoint;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;


// https://www.jmdoudoux.fr/java/dej/chap-api_websocket.htm#api_websocket-6

/**
 * Classe chargée de représenter la session d'un client. Elle est donc chargée
 * des communications client/serveur.
 */
@ServerEndpoint("/")
public class Session {
	/**
	 * Instance d'application utilisée pour requêter les données.
	 */
	private static Application application;

	/**
	 * Utilisateur propriétaire de la session.
	 */
	private Utilisateur utilisateur;

	/**
	 * Décodeur utilisé pour la gestion des requêtes client.
	 */
	private RequestDecoder decodeur;

	/**
	 * Objet session (WebSocket)
	 */
	private javax.websocket.Session session;

	/**
	 * Création d'une session.
	 */
	public Session() {
		super();
		this.decodeur = new RequestDecoder(this);
		System.out.println("Session créée");
	}

	/**
	 * Définir l'instance d'Application utilisée par toutes les sessions.
	 * @param applciation Instance d'Application utilisée.
	 */
	public static void setApplication(Application application) {
		Session.application = application;
	}

	/**
	 * Récupérer l'instance d'Application utilisée pour toutes les sessions.
	 * @return Instance d'Application utilisée.
	 */
	public static Application getApplication() { return Session.application; }

	/**
	 * Définir l'utilisateur propriétaire de cette session.
	 * @param utilisateur Utilisateur propriétaire de la session.
	 */
	public void setUtilisateur(Utilisateur utilisateur){
		this.utilisateur = utilisateur ;
	}

	/**
	 * Récupérer l'utilisateur propriétaire de la session.
	 * @return Utilisateur propriétaire de la session.
	 */
	public Utilisateur getUtilisateur(){
		return this.utilisateur ;
	}

	/**
	 * Action effectué à l'ouverture de la session.
	 * @param session -
	 */
	@OnOpen
	public void onOpen(javax.websocket.Session session) {
		System.out.println("IL EST OPEN");
		this.session = session;
	}

	/**
	 * Action effectué lors de la réception d'un message de la part d'un client.
	 * @param message Message reçu.
	 * @throws IOException Si la réponse au message n'a pas pu être envoyée.
	 */
	@OnMessage
	public void onMessage(String message) throws IOException {
		System.out.println("ON A UN PUTAIN DE MESSAGE MAGLE : ");
		System.out.println(message);
		this.session.getBasicRemote().sendText("Hey magle");
	}

	/**
	 * Action effectué à la fermeture de la session.
	 * @param session -
	 */
	@OnClose
	public void onClose(javax.websocket.Session session) {
		System.out.println("Et... Il est parti :'(");
	}

	/**
	 * Action effectué si une erreur survient entre le client et le serveur.
	 * @param throwable Erreur qui est survenue.
	 */
	@OnError
	public void onError(Throwable throwable) {

	}

	/**
	 * Action effectué à la fermeture de la session.
	 * @return Vrai si la session est correctement fermée. Faux sinon.
	 */
	public boolean fermer() {
		// try {
		// 	this.input.close();
		// 	this.output.close();
		// 	this.socketClient.close();

		// 	this.input = null;
		// 	this.output = null;
		// 	this.socketClient = null;

		// 	if (this.utilisateur instanceof UtilisateurHumain)
		// 		((UtilisateurHumain)this.utilisateur).setSession(null);
			System.out.println("Serveur correctement fermé.");
			return true;
		// }
		// catch (IOException ioe) {
		// 	return false;
		// }
		// return true;
	}
}