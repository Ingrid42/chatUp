package messagerie.serveur;

import messagerie.serveur.utilisateur.Utilisateur;

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

public class Serveur {
	//private ServerSocket server;
	private Server serveur;
	private Application application;

	public Serveur() {
		System.out.println("Initializing server...");
		if (initialize("serveur.save"))
			System.out.println("Server restored to its previous state!");
		else {
			this.application = Application.getInstance();
			System.out.println("Server initialized!");
		}

		Session.setApplication(this.application);
		/*try {
			server = new ServerSocket(4000, 100, InetAddress.getByName("localhost"));
			while(true) {
				 Session auth = new Session(server.accept());
				 Thread t = new Thread(auth);
				 t.start();
			}

		} 
		catch (UnknownHostException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}*/

		this.serveur = new Server("localhost", 4000, "/", null, Session.class);
	}

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

	public void enregistrer(String fileName) throws IOException{
		ObjectOutputStream oos = null;

		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
			oos.writeObject(application);

			if (oos != null)
				oos.close();

		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void start() throws DeploymentException {
		this.serveur.start();
	}

	public void stop() throws DeploymentException {
		
		this.serveur.stop();
	}

	public static void main(String[] args) {
		Serveur serveur = null;

		try {
			serveur = new Serveur();
			serveur.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Appuyez sur une touche pour stopper le serveur...");
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
					serveur.enregistrer("sauvegarde.save");
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
