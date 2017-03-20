package messagerie.serveur.discussion;

import java.util.ArrayList;
import java.util.List;

import messagerie.serveur.utilisateur.Utilisateur;
import java.io.Serializable;

public class Discussion implements Serializable {
    public final static long serialVersionUID = 531L;

    private static int idIncr = 0;
    private int id;
    private List<Utilisateur> utilisateurs ;

    public Discussion(List<Utilisateur> utilisateurs){
        this.id = Discussion.idIncr++;
        this.utilisateurs = new ArrayList<Utilisateur>(utilisateurs);

		for (Utilisateur u : this.utilisateurs)
			u.addDiscussion(this);
    }

    public List<Utilisateur> getUtilisateur(){
        return this.utilisateurs ;
    }
}
