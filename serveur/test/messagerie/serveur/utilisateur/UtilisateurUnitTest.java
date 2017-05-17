package test.messagerie.serveur.utilisateur;

import messagerie.serveur.utilisateur.Utilisateur;
import messagerie.serveur.discussion.DiscussionTexte;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.List;
import java.util.ArrayList;

public class UtilisateurUnitTest {
    private final static String PSEUDO_1 = "PSEUDO_1";
    private final static String PSEUDO_2 = "PSEUDO_2";
    private final static String PSEUDO_3 = "PSEUDO_3";

    private final static String NOM_1 = "NOM_1";
    private final static String NOM_2 = "NOM_2";
    private final static String NOM_3 = "NOM_3";

    private final static String PRENOM_1 = "PRENOM_1";
    private final static String PRENOM_2 = "PRENOM_2";
    private final static String PRENOM_3 = "PRENOM_3";

    private DiscussionTexte discussion;

    private Utilisateur utilisateur1;
    private Utilisateur utilisateur2;
    private Utilisateur utilisateur3;

    private List<Utilisateur> utilisateurs;

    @Before
    public void initialiseTests() {
        this.utilisateur1 = new Utilisateur(PSEUDO_1, NOM_1, PRENOM_1) {
            public final static long serialVersionUID = 951L;
            public void envoyerMessage(String message) {}
        };
        this.utilisateur2 = new Utilisateur(PSEUDO_2, NOM_2, PRENOM_2) {
            public final static long serialVersionUID = 951L;
            public void envoyerMessage(String message) {}
        };
        this.utilisateur3 = new Utilisateur(PSEUDO_3, NOM_3, PRENOM_3) {
            public final static long serialVersionUID = 951L;
            public void envoyerMessage(String message) {}
        };

        this.utilisateurs = new ArrayList<>();
        this.utilisateurs.add(utilisateur1);
        this.utilisateurs.add(utilisateur2);


    }

    @Test
    public void verifieGetMethodes() {
        // Pseudonyme
        assertThat(utilisateur1.getPseudonyme(), allOf(
            not(nullValue()),
            equalTo(PSEUDO_1),
            not(equalTo(PSEUDO_2))
        ));

        // Nom
        assertThat(utilisateur1.getNom(), allOf(
            not(nullValue()),
            equalTo(NOM_1),
            not(equalTo(NOM_2))
        ));

        // Prénom
        assertThat(utilisateur1.getPrenom(), allOf(
            not(nullValue()),
            equalTo(PRENOM_1),
            not(equalTo(PRENOM_2))
        ));
    }

    @Test
    public void verifieAjoutRetraitDiscussion() {
        assertThat(utilisateur1.getDiscussions(), not(nullValue()));

        try {
            this.discussion = new DiscussionTexte(utilisateurs);
        }
        catch (Exception e) {}

        assertThat(utilisateur1.getDiscussions(), allOf(
            not(nullValue()),
            hasItem(discussion)
        ));

        utilisateur1.removeDiscussion(discussion);

        assertThat(utilisateur1.getDiscussions(), allOf(
            not(nullValue()),
            not(hasItem(discussion))
        ));
    }
}