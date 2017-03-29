package messagerie.serveur.discussion;

import java.util.ArrayList;
import java.util.List;

import messagerie.serveur.utilisateur.Utilisateur;
import messagerie.serveur.exception.*;

import java.io.Serializable;

public class Discussion implements Serializable {
    public final static long serialVersionUID = 531L;

    private static int idIncr = 0;
    private int id;
    private List<Utilisateur> utilisateurs ;

    public Discussion(List<Utilisateur> utilisateurs) throws DiscussionException {
        if (utilisateurs.size() < 2)
            throw new DiscussionException("Une discussion doit avoir au minimum 2 utilisateurs.");
        
        this.id = Discussion.idIncr++;
        this.utilisateurs = new ArrayList<Utilisateur>(utilisateurs);

		for (Utilisateur u : this.utilisateurs)
			u.addDiscussion(this);
    }

    public List<Utilisateur> getUtilisateurs(){
        return this.utilisateurs ;
    }

    public long getId() { return this.id; }
}
