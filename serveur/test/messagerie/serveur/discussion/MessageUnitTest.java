package test.messagerie.serveur.discussion;

import messagerie.serveur.discussion.DiscussionTexte;
import messagerie.serveur.discussion.Message;
import messagerie.serveur.utilisateur.Utilisateur;
import messagerie.serveur.utilisateur.UtilisateurHumain;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class MessageUnitTest {
    private Message message1;
    private Message message2;
    private DiscussionTexte discussion;
    private List<Utilisateur> utilisateurs;
    private UtilisateurHumain utilisateur1;
    private UtilisateurHumain utilisateur2;

    private final static String MESSAGE_1_CONTENU = "CONTENU_1";
    private final static String MESSAGE_2_CONTENU = "CONTENU_2";

    @Before
    public void initialiseTests() {
        this.utilisateurs = new ArrayList<>();
        this.utilisateur1 = new UtilisateurHumain("PSEUDO_1", "NOM", "PRENOM", "MOT_DE_PASSE", "ADRESSE_MEL", new Date());
        this.utilisateur2 = new UtilisateurHumain("PSEUDO_2", "NOM", "PRENOM", "MOT_DE_PASSE", "ADRESSE_MEL", new Date());
        this.utilisateurs.add(utilisateur1);
        this.utilisateurs.add(utilisateur2);
        try { this.discussion = new DiscussionTexte(utilisateurs); } catch (Exception e) {}
        
        this.message1 = new Message(utilisateur1, MESSAGE_1_CONTENU, discussion);
    }

    @Test
    public void verifieGetMethodes() {
        // getMessage
        assertThat(message1.getMessage(), allOf(
                                            not(nullValue()), 
                                            equalTo(MESSAGE_1_CONTENU), 
                                            not(equalTo(MESSAGE_2_CONTENU))));
        // getDate
        assertThat(message1.getDate(), not(nullValue()));
        // getUtilisateur
        assertThat(message1.getUtilisateur(), allOf(
                                                not(nullValue()),
                                                equalTo(utilisateur1),
                                                not(equalTo(utilisateur2))));
        // getId
        assertThat(message1.getId(), equalTo(discussion.getId()));
    }
}