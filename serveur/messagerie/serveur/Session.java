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

public class Session implements Runnable{
	private Socket socketClient;
	private static Application application;
	private Utilisateur utilisateur ;
	private RequestDecoder decodeur;
	private InputStream input;
	private OutputStream output;

	public Session(Socket socketClient) {
		this.socketClient = socketClient;
		this.decodeur = new RequestDecoder(this);
		
		System.out.println("Session créée");
	}

	public boolean handshake() throws IOException, NoSuchAlgorithmException {
		String inputMessage = new Scanner(input, "UTF-8").useDelimiter("\\r\\n\\r\\n").next();
		Matcher get = Pattern.compile("^GET").matcher(inputMessage);

		if (get.find()) {
			Matcher match = Pattern.compile("Sec-WebSocket-Key: (.*)").matcher(inputMessage);
    		match.find();
   			byte[] response = ("HTTP/1.1 101 Switching Protocols\r\n"
								+ "Connection: Upgrade\r\n"
								+ "Upgrade: websocket\r\n"
								+ "Sec-WebSocket-Accept: "
								+ DatatypeConverter.printBase64Binary(
										MessageDigest
										.getInstance("SHA-1")
										.digest((match.group(1) + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11")
												.getBytes("UTF-8"))
								)
								+ "\r\n\r\n")
								.getBytes("UTF-8");

    		output.write(response, 0, response.length);
			System.out.println("Connexion établie!");
		}
		else {
			System.err.println("ERREUR : Connexion non établie!");
			return false;
		}
		return true;
	}

	public void recevoirMessage() throws IOException {
		String inputMessage = new Scanner(input, "UTF-8").next();
		System.out.println(inputMessage);
		/*String inputMessage = new Scanner(input, "UTF-8").useDelimiter("\\r\\n\\r\\n").next();

		if (get.find()) {
			this.decodeur.decode(inputMessage);
		}
		else {

		}*/
	}

	public synchronized void envoyerMessage(String message) {
		/*output.write(message);
		output.flush();*/
	}

	public Socket getSocketClient() { return this.socketClient; }

	public boolean fermer() {
		try {
			this.input.close();
			this.output.close();
			this.socketClient.close();

			this.input = null;
			this.output = null;
			this.socketClient = null;

			if (this.utilisateur instanceof UtilisateurHumain)
				((UtilisateurHumain)this.utilisateur).setSession(null);
			System.out.println("Serveur correctement fermé.");
		}
		catch (IOException ioe) {
			return false;
		}
		return true;
	}
	public void run(){
		try {
			this.input = this.socketClient.getInputStream();
			this.output = this.socketClient.getOutputStream();

			this.handshake();

			do {
				this.recevoirMessage();
			} while ( this.socketClient != null);
		}
		catch (IOException ioe) {
			System.err.println("ERREUR : Impossible de recevoir un message de la part de ce client.");
			System.err.println(ioe.getMessage());
			System.err.println("Fermeture de la session.");
		}
		catch (Exception e) {
			System.err.println("ERREUR : Une erreur inattendue est survenue!");
			System.err.println(e.getMessage());
			System.err.println("Fermeture de la session.");
		}
		finally {
			this.fermer();
		}
	}

	public static void setApplication(Application application) {
		Session.application = application;
	}

	public static Application getApplication() { return Session.application; }

	public void setUtilisateur(Utilisateur utilisateur){
		this.utilisateur = utilisateur ;
	}
	public Utilisateur getUtilisateur(){
		return this.utilisateur ;
	}
}
