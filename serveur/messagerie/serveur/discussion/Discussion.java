package messagerie.serveur.discussion;

import java.util.ArrayList;
import java.util.List;

import messagerie.serveur.utilisateur.Utilisateur;
import messagerie.serveur.exception.*;

import java.io.Serializable;

/**
 * Classe représentant une discussion entre utilisateurs.
 */
public class Discussion implements Serializable {
    public final static long serialVersionUID = 531L;

    /**
     * Indice autoincrémenté afin d'identifier chaque nouvelle discussion.
     */
    private static int idIncr = 0;

    /**
     * Identifiant de la discussion.
     */
    private int id;

    /**
     * Liste des utilisateurs prennant part à la discussion.
     */
    private List<Utilisateur> utilisateurs ;

    /**
     * Création d'une discussion.
     * @param utilisateurs Liste des utilisateurs prennant part à la discussion.
     * @throws DiscussionException Se déclenche si il y a moins de 2 utilisateurs prennant part à la discussion.
     */ 
    public Discussion(List<Utilisateur> utilisateurs) throws DiscussionException {
        if (utilisateurs.size() < 2)
            throw new DiscussionException("Une discussion doit avoir au minimum 2 utilisateurs.");
        
        this.id = Discussion.idIncr++;
        this.utilisateurs = new ArrayList<Utilisateur>(utilisateurs);

		for (Utilisateur u : this.utilisateurs)
			u.addDiscussion(this);
    }

    /**
     * Récupérer la liste des utilisateurs présents dans la discussion.
     * @return Liste des utilisateurs prennant part à la discussion.
     */
    public List<Utilisateur> getUtilisateurs(){
        return this.utilisateurs ;
    }

    /**
     * Récupérer l'identifiant de la discussion.
     * @return Identifiant de la discussion.
     */
    public long getId() { return this.id; }
}
