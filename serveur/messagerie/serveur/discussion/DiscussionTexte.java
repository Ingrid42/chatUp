package messagerie.serveur.discussion;

import java.util.ArrayList;
import java.util.List;

import messagerie.serveur.utilisateur.Utilisateur;
import messagerie.serveur.exceptions.*;

/**
 * Classe représentant une discussion texte entre utilisateurs.
 */
public class DiscussionTexte extends Discussion {
    public final static long serialVersionUID = 7521L;

    /**
     * Messages envoyés par les utilisateurs de la discussion.
     */
    private List<Message> messages;
    
    /**
     * Création d'une discussion texte.
     * @param utilisateurs Liste des utilisateurs prennant part à la discussion.
     * @throws DiscussionException Se déclenche si il y a moins de 2 utilisateurs prennant part à la discussion.
     */
    public DiscussionTexte(List<Utilisateur> utilisateurs) throws DiscussionException {
        super(utilisateurs);
        this.messages = new ArrayList<Message>();
    }

    /**
     * Ajouter un message à la discussion.
     * @param message Message à ajouter.
     * @return Vrai si le message a pu être ajouté. Faux sinon.
     */
    public boolean addMessage(Message message){
        return messages.add(message);
    }

    /**
     * Récupérer la liste des messages de la discussion courante.
     * @return Liste des messages envoyés par les utilisateurs.
     */
    public List<Message> getMessages(){
        return this.messages;
    }
}
