package messagerie.serveur.discussion;
import java.io.Serializable;

import messagerie.serveur.utilisateur.Utilisateur;

public class Message implements Serializable {
    public final static long serialVersionUID = 1257L;

    private String message; 
    private Utilisateur utilisateur;
    
    public Message(Utilisateur utilisateur, String message){
        this.message = message ;
        this.utilisateur = utilisateur ;
    }
}