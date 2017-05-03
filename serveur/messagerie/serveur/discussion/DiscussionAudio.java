package messagerie.serveur.discussion;
import java.util.ArrayList;
import java.util.List;

import messagerie.serveur.utilisateur.Utilisateur;
import messagerie.serveur.exception.*;

/**
 * Classe représentant une discussion audio entre utilisateurs.
 */
public class DiscussionAudio extends Discussion{
    public final static long serialVersionUID = 6854L;
    
    /**
     * Création d'une discussion audio.
     * @param utilisateurs Liste des utilisateurs prennant part à la discussion.
     * @throws DiscussionException Se déclenche si il y a moins de 2 utilisateurs prennant part à la discussion.
     */
    public DiscussionAudio(List<Utilisateur> utilisateurs) throws DiscussionException {
        super(utilisateurs);
    }
    
    /**
     * Démarrer une discussion audio entre les utilisateurs.
     * @return Vrai si la discussion a pu être démarrée. Faux sinon.
     */
    public boolean demarrer(){
        // sauvegarde du temps de debut
        return true ;
    }

    /**
     * Arrêter une discussion audio entre les utilisateurs.
     * @return Vrai si la discussion a pu être arrêtée. Faux sinon.
     */
    public boolean arreter(){
        // sauvegarde du temps de fin
        return true ; 
    }
    
}