package test.messagerie.serveur.utilisateur;

import messagerie.serveur.utilisateur.Utilisateur;
import messagerie.serveur.utilisateur.UtilisateurHumain;
import messagerie.serveur.discussion.DiscussionTexte;
import messagerie.serveur.discussion.Message;
import messagerie.serveur.filtre.*;

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
import java.util.Date;
import java.util.GregorianCalendar;

public class UtilisateurHumainUnitTest {
    private final static String PSEUDO_1 = "PSEUDO_1";
    private final static String PSEUDO_2 = "PSEUDO_2";
    private final static String PSEUDO_3 = "PSEUDO_3";

    private final static String NOM_1 = "NOM_1";
    private final static String NOM_2 = "NOM_2";
    private final static String NOM_3 = "NOM_3";

    private final static String PRENOM_1 = "PRENOM_1";
    private final static String PRENOM_2 = "PRENOM_2";
    private final static String PRENOM_3 = "PRENOM_3";

    private final static String MOT_DE_PASSE_1 = "MOT_DE_PASSE_1";
    private final static String MOT_DE_PASSE_2 = "MOT_DE_PASSE_2";
    private final static String MOT_DE_PASSE_3 = "MOT_DE_PASSE_3";

    private final static String ADRESSE_MEL_1 = "ADRESSE_MEL_1";
    private final static String ADRESSE_MEL_2 = "ADRESSE_MEL_2";
    private final static String ADRESSE_MEL_3 = "ADRESSE_MEL_3";

    private final static String PHOTO_1 = "PHOTO_1";
    private final static String PHOTO_2 = "PHOTO_2";

    private final static Date DATE_1 = new GregorianCalendar(2017, 5, 17).getTime();
    private final static Date DATE_2 = new GregorianCalendar(2017, 5, 18).getTime();
    private final static Date DATE_3 = new GregorianCalendar(2017, 5, 19).getTime();

    private final static String MESSAGE = "MESSAGE MOT_1 MOT_2 MOT_3";
    private final static String MOT_FILTRE = "MOT_2";
    private final static String MESSAGE_FILTRE = "MESSAGE MOT_1 ***** MOT_3";

    private DiscussionTexte discussion;

    private UtilisateurHumain utilisateur1;
    private UtilisateurHumain utilisateur2;
    private UtilisateurHumain utilisateur3;

    private List<Utilisateur> utilisateurs;

    @Before
    public void initialiseTests() {
        this.utilisateur1 = new UtilisateurHumain(PSEUDO_1, NOM_1, PRENOM_1, MOT_DE_PASSE_1, ADRESSE_MEL_1, DATE_1);
        this.utilisateur2 = new UtilisateurHumain(PSEUDO_2, NOM_2, PRENOM_2, MOT_DE_PASSE_2, ADRESSE_MEL_2, DATE_2);
        this.utilisateur3 = new UtilisateurHumain(PSEUDO_3, NOM_3, PRENOM_3, MOT_DE_PASSE_3, ADRESSE_MEL_3, DATE_3);

        this.utilisateurs = new ArrayList<>();
        this.utilisateurs.add(utilisateur1);
        this.utilisateurs.add(utilisateur2);

        try {
            this.discussion = new DiscussionTexte(utilisateurs);
        }
        catch (Exception de) {
            de.printStackTrace();
        }
    }

    @Test
    public void verifieGetMethodes() {
        // Adresse mel
        assertThat(utilisateur1.getAdresseMel(), allOf(
            not(nullValue()),
            equalTo(ADRESSE_MEL_1),
            not(equalTo(ADRESSE_MEL_2))
        ));

        // Photo
        assertThat(utilisateur1.getPhoto(), allOf(
            not(nullValue()),
            equalTo(UtilisateurHumain.photoDefaut),
            not(equalTo(PHOTO_2))
        ));

        // Date
        assertThat(utilisateur1.getDateNaissance(), allOf(
            not(nullValue()),
            equalTo(DATE_1),
            not(equalTo(DATE_2))
        ));
    }

    @Test
    public void verifieSetMethodes() {
        // Adresse mel
        utilisateur1.setAdresseMel(ADRESSE_MEL_3);
        assertThat(utilisateur1.getAdresseMel(), allOf(
            not(nullValue()),
            equalTo(ADRESSE_MEL_3),
            not(equalTo(ADRESSE_MEL_2))
        ));

        // Photo
        utilisateur1.setPhoto(PHOTO_1);
        assertThat(utilisateur1.getPhoto(), allOf(
            not(nullValue()),
            equalTo(PHOTO_1),
            not(equalTo(PHOTO_2))
        ));

        // Date
        utilisateur1.setDateNaissance(DATE_3);
        assertThat(utilisateur1.getDateNaissance(), allOf(
            not(nullValue()),
            equalTo(DATE_3),
            not(equalTo(DATE_2))
        ));
    }

    @Test
    public void verifieMotDePasse() {
        assertThat(true, allOf(
            equalTo(utilisateur1.verifieMotDePasse(MOT_DE_PASSE_1)),
            not(equalTo(utilisateur1.verifieMotDePasse(MOT_DE_PASSE_2)))
        ));
    }

    @Test
    public void verifieSetMotDePasse() {
        utilisateur1.setMotDePasse(MOT_DE_PASSE_3);

        assertThat(true, allOf(
            equalTo(utilisateur1.verifieMotDePasse(MOT_DE_PASSE_3)),
            not(equalTo(utilisateur1.verifieMotDePasse(MOT_DE_PASSE_2)))
        ));
    }

    @Test
    public void verifieMotDePasseParentalDefaut() {
        assertThat(true, allOf(
            equalTo(utilisateur1.verifieMotDePasseParental(null)),
            not(equalTo(utilisateur1.verifieMotDePasseParental(MOT_DE_PASSE_1)))
        ));
    }

    @Test
    public void verifieSetMotDePasseParental() {
        utilisateur1.setMotDePasseParental(MOT_DE_PASSE_3);

        assertThat(true, allOf(
            equalTo(utilisateur1.verifieMotDePasseParental(MOT_DE_PASSE_3)),
            not(equalTo(utilisateur1.verifieMotDePasseParental(MOT_DE_PASSE_2)))
        ));
    }

    @Test
    public void verifieFiltres() {
        Message message = new Message(utilisateur1, MESSAGE, discussion);
        IFiltre filtreMot = new FiltreMot(MOT_FILTRE);

        assertThat(utilisateur2.filtrerMessage(message), allOf(
            not(nullValue()),
            equalTo(MESSAGE),
            not(equalTo(MESSAGE_FILTRE))
        ));

        utilisateur2.ajouterFiltre(filtreMot);

        assertThat(utilisateur2.filtrerMessage(message), allOf(
            not(nullValue()),
            equalTo(MESSAGE_FILTRE),
            not(equalTo(MESSAGE))
        ));

        utilisateur2.retirerFiltre(filtreMot);

        assertThat(utilisateur2.filtrerMessage(message), allOf(
            not(nullValue()),
            equalTo(MESSAGE),
            not(equalTo(MESSAGE_FILTRE))
        ));
    }
}