package messagerie.serveur.discussion;
import messagerie.serveur.utilisateur.Utilisateur;

public class Message{
    private String message; 
    private Utilisateur utilisateur;
    
    public Message(Utilisateur utilisateur, String message){
        this.message = message ;
        this.utilisateur = utilisateur ;
    }
}