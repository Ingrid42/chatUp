package messagerie.serveur;

import messagerie.serveur.utilisateur.Utilisateur;
import java.io.IOException;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;

import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class Serveur {
	private ServerSocket server;
	private Application application;

	public Serveur(){
		try {
			server = new ServerSocket(4000, 100, InetAddress.getByName("localhost"));
			int i = 0;
			while(i<10){    
				 Session auth = new Session(server.accept(), application);
				 Thread t = new Thread(auth);
				 t.start(); 
				 i++;
			}

		} 
		catch (UnknownHostException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
	}





























	public void initialize(String fileName) {
		ObjectInputStream ois = null;
		File fichier = new File(fileName);

		if (fichier.exists()){
			try {
				ois = new ObjectInputStream(new FileInputStream(fichier));
				this.application = (Application)ois.readObject();
				
				if (ois != null) 
					ois.close();

			} catch(IOException e) {
				e.printStackTrace();
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
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
}
