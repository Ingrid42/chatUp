package messagerie.serveur.discussion;
import java.util.ArrayList;
import java.util.List;
import messagerie.serveur.utilisateur.Utilisateur;

public class Discussion{
    private ArrayList<Utilisateur> utilisateurs ;
    public Discussion(ArrayList<Utilisateur> utilisateurs){
        this.utilisateurs = utilisateurs;
    }
    public ArrayList<Utilisateur> getUtilisateur(){
        return this.utilisateurs ;
    }
}