package test.messagerie.serveur.discussion;

import messagerie.serveur.discussion.Discussion;
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

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class DiscussionUnitTest {
    private Discussion discussion1;
    private Discussion discussion2;
    private List<Utilisateur> utilisateurs;
    private UtilisateurHumain utilisateur1;
    private UtilisateurHumain utilisateur2;
    private UtilisateurHumain utilisateur3;
    // private Message message1;
    // private Message message2;
    
    // private final static String MESSAGE_1_CONTENU = "CONTENU_1";
    // private final static String MESSAGE_2_CONTENU = "CONTENU_2";

    @Before
    public void initialiseTests() {
        this.utilisateurs = new ArrayList<>();
        this.utilisateur1 = new UtilisateurHumain("PSEUDO_1", "NOM", "PRENOM", "MOT_DE_PASSE", "ADRESSE_MEL", new Date());
        this.utilisateur2 = new UtilisateurHumain("PSEUDO_2", "NOM", "PRENOM", "MOT_DE_PASSE", "ADRESSE_MEL", new Date());
        this.utilisateur3 = new UtilisateurHumain("PSEUDO_3", "NOM", "PRENOM", "MOT_DE_PASSE", "ADRESSE_MEL", new Date());
        this.utilisateurs.add(utilisateur1);
        this.utilisateurs.add(utilisateur2);
        try { this.discussion1 = new Discussion(utilisateurs); } catch (Exception e) {}
        try { this.discussion2 = new Discussion(utilisateurs); } catch (Exception e) {}

        // this.message1 = new Message(utilisateur1, MESSAGE_1_CONTENU, discussion);
        // this.message2 = new Message(utilisateur2, MESSAGE_2_CONTENU, discussion);
    }

    @Test
    public void verifieUtilisateurs() {
        assertThat(discussion1.getUtilisateurs(), allOf(
            not(nullValue()),
            hasItem(utilisateur1),
            hasItem(utilisateur2),
            not(hasItem(utilisateur3))
        ));
    }

    @Test
    public void verifiePossedeUtilisateur() {
        assertThat(discussion1.possedeUtilisateur(utilisateur1), is(true));
        assertThat(discussion1.possedeUtilisateur(utilisateur2), is(true));
        assertThat(discussion1.possedeUtilisateur(utilisateur3), is(false));
    }

    @Test
    public void verifieId() {
        assertThat(true, is(discussion1.getId() < discussion2.getId()));
    }
}