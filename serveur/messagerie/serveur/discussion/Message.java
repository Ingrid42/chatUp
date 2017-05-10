package messagerie.serveur.discussion;
import java.io.Serializable;

import messagerie.serveur.utilisateur.Utilisateur;

import java.util.Date;

/**
 * Classe représentant un message.
 */
public class Message implements Serializable {
    public final static long serialVersionUID = 1257L;

    /**
     * Identifiant de la discussion à laquelle ce message se rattache.
     */
    private long idDiscussion ;

    /**
     * Contenu du message.
     */
    private String message;

    /**
     * Utilisateur ayant envoyé le message.
     */
    private Utilisateur utilisateur;

    /**
     * Date de création du message.
     */
    private Date date;

    /**
     * Créer un message.
     * @param utilisateur Utilisateur ayant écrit le message.
     * @param message Contenu du message envoyé.
     * @param idDiscussion Identifiant de la discussion à laquelle le message se rattache.
     */
    public Message(Utilisateur utilisateur, String message, DiscussionTexte discussion){
        this.message = message ;
        this.utilisateur = utilisateur ;
        this.idDiscussion = discussion.getId();
        this.date = new Date();

        discussion.addMessage(this);
    }

    /**
     * Récupérer le contenu du message.
     * @return Contenu du message.
     */
    public String getMessage(){
      return this.message;
    }

    /**
     * Récupérer la date de création du message.
     * @return Date de création du message.
     */
    public Date getDate(){
      return this.date;
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
    public long getId(){
      return this.idDiscussion ;
    }
}
