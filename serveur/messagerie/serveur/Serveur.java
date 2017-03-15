package messagerie.serveur;

import messagerie.serveur.utilisateur.Utilisateur;
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;

public class Serveur {
    private ServerSocket server ;
    public Serveur(){
        try {
            server = new ServerSocket(4000, 100, InetAddress.getByName("localhost"));      
            Socket client = server.accept();
        
        } 
        catch (UnknownHostException e) {e.printStackTrace();} 
        catch (IOException e) {e.printStackTrace();}
        
    }
}
