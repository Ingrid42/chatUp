package test.messagerie.serveur.filtre;

import messagerie.serveur.filtre.IFiltre;
import messagerie.serveur.filtre.FiltreUtilisateur;
import messagerie.serveur.utilisateur.Utilisateur;
import messagerie.serveur.utilisateur.UtilisateurHumain;

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

import java.util.Date;

public class FiltreUtilisateurUnitTest {
    private FiltreUtilisateur filtre;

    private final static Utilisateur UTILISATEUR_1 = new UtilisateurHumain("PSEUDO_1", "NOM", "PRENOM", "MOT_DE_PASSE", "ADRESSE_MEL", new Date());
    private final static Utilisateur UTILISATEUR_2 = new UtilisateurHumain("PSEUDO_2", "NOM", "PRENOM", "MOT_DE_PASSE", "ADRESSE_MEL", new Date());

    @Before
    public void initialiseTests() {
        this.filtre = new FiltreUtilisateur(UTILISATEUR_1);
    }

    @Test
    public void verifieImplements() {
        assertThat(filtre, instanceOf(IFiltre.class));
    }

    @Test
    public void verifieObjet() {
        assertThat(filtre.getObject(), allOf(
            not(nullValue()),
            instanceOf(Utilisateur.class),
            equalTo(UTILISATEUR_1),
            not(equalTo(UTILISATEUR_2))
        ));
    }

    @Test
    public void verifieComparaison() {
        assertThat(true, allOf(
            is(filtre.compare(UTILISATEUR_1)),
            not(is(filtre.compare(UTILISATEUR_2)))
        ));
    }
}