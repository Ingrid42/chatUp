package messagerie.serveur;

import messagerie.serveur.exceptions.UtilisateurException;
import messagerie.serveur.utilisateur.Utilisateur;
import messagerie.serveur.utilisateur.UtilisateurHumain;

import java.net.UnknownHostException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;

import java.io.IOException;
import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.websocket.DeploymentException;
import org.glassfish.tyrus.server.Server;

import java.util.List;
import java.util.ArrayList;

/**
 * Classe chargée de démarrer le serveur. Celui-ci a pour objectif d'établir les 
 * communications avec les clients, ainsi que de restaurer les données après redémarrage de celui-ci.
 */
public class Serveur {
	/**
	 * Objet serveur
	 */
	private Server serveur;

	/**
	 * Instance d'application utilisée.
	 */
	private Application application;

	/**
	 * Création d'un objet serveur.
	 */
	public Serveur() {
		System.out.println("Initialisation du serveur...");
		if (initialize("serveur.save")) {
			System.out.println("Server restoré à son précédent état!");
			System.out.println("Liste des utilisateurs :");
			System.out.println(this.application.getUtilisateurs());
		}
		else {
			this.application = Application.getInstance();
			
			UtilisateurHumain utilisateur1 = new UtilisateurHumain(
												"root",
												"YouYou",
												"SsefSsef",
												"root",
												"un_mail@insa-rouen.fr",
												new java.util.Date()
											);
			UtilisateurHumain utilisateur2 = new UtilisateurHumain(
												"user",
												"Thibaut",
												"Sans L c'est mieux",
												"user",
												"un_mail_2@insa-rouen.fr",
												new java.util.Date()
											);

			try {
				this.application.ajouterUtilisateur(utilisateur1);
				this.application.ajouterUtilisateur(utilisateur2);
			}
			catch (Exception ue) {
				System.err.println("Erreur lors de l'initialisation des utilisateurs par défaut.");
				System.err.println(ue.getMessage());
				System.err.println(ue.getCause());
			}

			System.out.println("Serveur initialisé!");
		}

		Session.setApplication(this.application);
		this.serveur = new Server("localhost", 4000, "/", null, Session.class);
	}

	/**
	 * Récupérer une précédente instance d'Application si le serveur a déjà été exécuté.
	 * @param fileName Nom du fichier de sauvegarde.
	 * @return Vrai si l'instance d'Application a pu être récupérée. Faux sinon.
	 */
	public boolean initialize(String fileName) {
		ObjectInputStream ois = null;
		File fichier = new File(fileName);

		if (fichier.exists()){
			try {
				ois = new ObjectInputStream(new FileInputStream(fichier));
				this.application = (Application)ois.readObject();
				
				if (ois != null) 
					ois.close();

				return true;

			} catch(IOException e) {
				e.printStackTrace();
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * Enregistrer l'instance d'Application.
	 * @param fileName Fichier de sauvegarde.
	 * @throws IOException Si la sauvegarde n'a pas pu être effectuée.
	 */
	public void enregistrer(String fileName) throws IOException {
		ObjectOutputStream oos = null;

		oos = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
		oos.writeObject(application);
		oos.flush();

		if (oos != null)
			oos.close();
	}

	/**
	 * Démarre le serveur afin qu'il écoute les clients.
	 * @throws DeploymentException Si le serveur n'a pas pu être démarré.
	 */
	public void start() throws DeploymentException {
		this.serveur.start();
	}

	/**
	 * Arrête le serveur.
	 * @throws DeploymentException Si le serveur n'a pas pu être arrêté.
	 */
	public void stop() throws DeploymentException {
		Session.closeAllSessions();
		this.serveur.stop();
	}

	public static void main(String[] args) {
		Serveur serveur = null;

		try {
			serveur = new Serveur();
			serveur.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Appuyez sur 'Entrée' pour stopper le serveur...");
			reader.readLine();
		}
		catch (DeploymentException de) {
			de.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (serveur != null) {
					serveur.enregistrer("serveur.save");
					serveur.stop();
				}
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			catch (DeploymentException de) {
				de.printStackTrace();
			}
		}
	}
}
