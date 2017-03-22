package messagerie.serveur;

import java.net.Socket;
import java.io.IOException;

public class Session implements Runnable{
	private Socket socketClient;
	private static Application application;

	public Session(Socket socketClient) {
		this.socketClient = socketClient;
		
	}

	public Socket getSocketClient() { return this.socketClient; }

	public boolean fermer() {
		try {
			this.socketClient.close();
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
}