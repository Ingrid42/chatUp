package messagerie.serveur.discussion;
import java.util.ArrayList;
import java.util.List;
import messagerie.serveur.utilisateur.Utilisateur;

public class DiscussionTexte extends Discussion{
    public final static long serialVersionUID = 7521L;

    private ArrayList<Message> messages;
    
    public DiscussionTexte(ArrayList<Utilisateur> utilisateurs){
        super(utilisateurs);
        this.messages = new ArrayList<Message>();
    }
    public boolean addMessage(Message message){
        return messages.add(message);
    }
    public ArrayList<Message> getMessages(){
        return this.messages;
    }
}