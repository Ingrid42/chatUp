package messagerie.serveur;

import java.net.Socket;
import java.io.IOException;
import messagerie.serveur.utilisateur.*;

public class Session implements Runnable{
	private Socket socketClient;
	private static Application application;
	private Utilisateur utilisateur ;

	public Session(Socket socketClient) {
		this.socketClient = socketClient;
		System.out.println("Session créée");
	}

	public Socket getSocketClient() { return this.socketClient; }

	public boolean fermer() {
		try {
			this.socketClient.close();

			if (this.utilisateur instanceof UtilisateurHumain)
				((UtilisateurHumain)this.utilisateur).setSession(null);
		}
		catch (IOException ioe) {
			return false;
		}
		return true;
	}
	public void run(){
		// get user id and password
		// check in the db if it exists
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
