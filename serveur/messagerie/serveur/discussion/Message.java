package messagerie.serveur.discussion;
import java.io.Serializable;

import messagerie.serveur.utilisateur.Utilisateur;

/**
 * Classe représentant un message.
 */
public class Message implements Serializable {
    public final static long serialVersionUID = 1257L;

    /**
     * Identifiant de la discussion à laquelle ce message se rattache.
     */
    private int idDiscussion ;

    /**
     * Contenu du message.
     */
    private String message;

    /**
     * Utilisateur ayant envoyé le message.
     */
    private Utilisateur utilisateur;

    /**
     * Créer un message.
     * @param utilisateur Utilisateur ayant écrit le message.
     * @param message Contenu du message envoyé.
     * @param idDiscussion Identifiant de la discussion à laquelle le message se rattache.
     */
    public Message(Utilisateur utilisateur, String message, int idDiscussion){
        this.message = message ;
        this.utilisateur = utilisateur ;
        this.idDiscussion = idDiscussion ;
    }

    /**
     * Récupérer le contenu du message.
     * @return Contenu du message.
     */
    public String getMessage(){
      return this.message;
    }

    /**
     * Récupérer l'auteur du message.
     * @return Utilisateur ayant écrit le message.
     */
    public Utilisateur getUtilisateur(){
      return this.utilisateur ;
    }

    /**
     * Récupérer l'identifiant de la discussion à laquelle se rattache ce message.
     * @return Identifiant d'une' discussion.
     */
    public int getId(){
      return this.idDiscussion ;
    }
}
