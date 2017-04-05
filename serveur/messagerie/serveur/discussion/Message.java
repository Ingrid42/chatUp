package messagerie.serveur.discussion;
import java.io.Serializable;

import messagerie.serveur.utilisateur.Utilisateur;

public class Message implements Serializable {
    public final static long serialVersionUID = 1257L;
    private int id_discussion ;
    private String message;
    private Utilisateur utilisateur;

    public Message(Utilisateur utilisateur, String message, int id_discussion){
        this.message = message ;
        this.utilisateur = utilisateur ;
        this.id_discussion = id_discussion ;
    }
    public String getMessage(){
      return this.message;
    }
    public Utilisateur getUtilisateur(){
      return this.utilisateur ;
    }
    public int getId(){
      return this.id_discussion ;
    }
}
