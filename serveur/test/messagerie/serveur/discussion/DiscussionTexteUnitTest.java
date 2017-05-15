package test.messagerie.serveur.discussion;

import messagerie.serveur.discussion.Discussion;
import messagerie.serveur.discussion.DiscussionTexte;
import messagerie.serveur.discussion.Message;
import messagerie.serveur.utilisateur.Utilisateur;
import messagerie.serveur.utilisateur.UtilisateurHumain;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class DiscussionTexteUnitTest {
    private DiscussionTexte discussion1;
    private List<Utilisateur> utilisateurs;
    private UtilisateurHumain utilisateur1;
    private UtilisateurHumain utilisateur2;
    private Message message1;
    private Message message2;
    
    private final static String MESSAGE_1_CONTENU = "CONTENU_1";
    private final static String MESSAGE_2_CONTENU = "CONTENU_2";

    @Before
    public void initialiseTests() {
        this.utilisateurs = new ArrayList<>();
        this.utilisateur1 = new UtilisateurHumain("PSEUDO_1", "NOM", "PRENOM", "MOT_DE_PASSE", "ADRESSE_MEL", new Date());
        this.utilisateur2 = new UtilisateurHumain("PSEUDO_2", "NOM", "PRENOM", "MOT_DE_PASSE", "ADRESSE_MEL", new Date());
        this.utilisateurs.add(utilisateur1);
        this.utilisateurs.add(utilisateur2);
        try { this.discussion1 = new DiscussionTexte(utilisateurs); } catch (Exception e) {}

        this.message1 = new Message(utilisateur1, MESSAGE_1_CONTENU, discussion1);
        this.message2 = new Message(utilisateur2, MESSAGE_2_CONTENU, discussion1);
    }

    @Test
    public void verifieImplements() {
        assertThat(discussion1, instanceOf(Discussion.class));
    }

    @Test
    public void verifieMessages() {
        assertThat(discussion1.getMessages(), allOf(
            not(nullValue()),
            not(hasItem(message1)),
            not(hasItem(message2))
        ));

        this.discussion1.addMessage(message1);
        this.discussion1.addMessage(message2);

        assertThat(discussion1.getMessages(), allOf(
            not(nullValue()),
            hasItem(message1),
            hasItem(message2)
        ));
    }
}