package messagerie.serveur.discussion;
import java.util.ArrayList;
import java.util.List;
import messagerie.serveur.utilisateur.Utilisateur;

public class DiscussionAudio extends Discussion{
    public final static long serialVersionUID = 6854L;
    
    public DiscussionAudio(List<Utilisateur> utilisateurs){
        super(utilisateurs);
    }
    
    public boolean demarrer(){
        // sauvegarde du temps de debut
        return true ;
    }
    public boolean arreter(){
        // sauvegarde du temps de fin
        return true ; 
    }
    
}