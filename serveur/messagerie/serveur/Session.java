package messagerie.serveur;

import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import messagerie.serveur.RequestDecoder;
import messagerie.serveur.utilisateur.*;

public class Session implements Runnable{
	private Socket socketClient;
	private static Application application;
	private Utilisateur utilisateur ;
	private RequestDecoder decodeur;
	private BufferedReader input;
	private PrintWriter output;

	public Session(Socket socketClient) {
		this.socketClient = socketClient;
		this.decodeur = new RequestDecoder(this);
		
		System.out.println("Session créée");
	}

	public void recevoirMessage() throws IOException {
		StringBuilder inputMessage = new StringBuilder();
		while (!input.ready()) { try { this.wait(1000); } catch (InterruptedException ie) {} } // Wait one second before checking if a message is received

		// Read the entire message
		while (input.ready()) {
			inputMessage.append(input.readLine());
		}

		this.decodeur.decode(inputMessage.toString());
	}

	public synchronized void envoyerMessage(String message) {
		output.write(message);
		output.flush();
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
			this.input = new BufferedReader(new InputStreamReader(this.socketClient.getInputStream()));
			this.output = new PrintWriter(this.socketClient.getOutputStream(), true);

			String inputMessage = null;
			String outputMessage = null;
			
			do {
				this.recevoirMessage();
			} while ( this.socketClient != null);
		}
		catch (IOException ioe) {
			System.err.println("ERREUR : Impossible de recevoir un message de la part de ce client, fermeture de la session.");
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
