package messagerie.serveur.discussion;
import java.util.ArrayList;
import java.util.List;

import messagerie.serveur.utilisateur.Utilisateur;
import messagerie.serveur.exceptions.*;

/**
 * Classe représentant une discussion audio entre utilisateurs.
 */
public class DiscussionAudio extends Discussion{
    public final static long serialVersionUID = 6854L;

    /**
     * Discussion state.
     */
     private boolean estDemarre;
    
    /**
     * Création d'une discussion audio.
     * @param utilisateurs Liste des utilisateurs prennant part à la discussion.
     * @throws DiscussionException Se déclenche si il y a moins de 2 utilisateurs prennant part à la discussion.
     */
    public DiscussionAudio(List<Utilisateur> utilisateurs) throws DiscussionException {
        super(utilisateurs);
        this.estDemarre = false;
    }
    
    /**
     * Démarrer une discussion audio entre les utilisateurs.
     * @return Vrai si la discussion a pu être démarrée. Faux sinon.
     */
    public boolean demarrer(){
        // sauvegarde du temps de debut
        if (estDemarre)
            return false;

        this.estDemarre = true;
        return this.estDemarre;
    }

    /**
     * Arrêter une discussion audio entre les utilisateurs.
     * @return Vrai si la discussion a pu être arrêtée. Faux sinon.
     */
    public boolean arreter(){
        // sauvegarde du temps de fin
        if (!estDemarre)
            return false;

        this.estDemarre = false;
        return !this.estDemarre; 
    }
    
}
