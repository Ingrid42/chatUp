package messagerie.serveur.discussion;

import java.util.ArrayList;
import java.util.List;

import messagerie.serveur.utilisateur.Utilisateur;

public class Discussion{
    private List<Utilisateur> utilisateurs ;

    public Discussion(List<Utilisateur> utilisateurs){
        this.utilisateurs = new ArrayList<Utilisateur>(utilisateurs);

		for (Utilisateur u : this.utilisateurs)
			u.addDiscussion(this);
    }

    public List<Utilisateur> getUtilisateur(){
        return this.utilisateurs ;
    }
}
