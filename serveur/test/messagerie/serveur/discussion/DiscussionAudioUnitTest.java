package test.messagerie.serveur.discussion;

import messagerie.serveur.discussion.Discussion;
import messagerie.serveur.discussion.DiscussionAudio;
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

public class DiscussionAudioUnitTest {
    private DiscussionAudio discussion1;
    private List<Utilisateur> utilisateurs;
    private UtilisateurHumain utilisateur1;
    private UtilisateurHumain utilisateur2;

    @Before
    public void initialiseTests() {
        this.utilisateurs = new ArrayList<>();
        this.utilisateur1 = new UtilisateurHumain("PSEUDO_1", "NOM", "PRENOM", "MOT_DE_PASSE", "ADRESSE_MEL", new Date());
        this.utilisateur2 = new UtilisateurHumain("PSEUDO_2", "NOM", "PRENOM", "MOT_DE_PASSE", "ADRESSE_MEL", new Date());
        this.utilisateurs.add(utilisateur1);
        this.utilisateurs.add(utilisateur2);
        try { this.discussion1 = new DiscussionAudio(utilisateurs); } catch (Exception e) {}
    }

    @Test
    public void verifieImplements() {
        assertThat(discussion1, instanceOf(Discussion.class));
    }

    @Test
    public void verifieDemarrage() {
        assertThat(false, is(discussion1.arreter()));
        assertThat(true, is(discussion1.demarrer()));
        assertThat(false, is(discussion1.demarrer()));
        assertThat(true, is(discussion1.arreter()));
    }
}