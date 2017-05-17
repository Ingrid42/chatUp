package test.messagerie.serveur.filtre;

import messagerie.serveur.filtre.IFiltre;
import messagerie.serveur.filtre.FiltreMot;

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

public class FiltreMotUnitTest {
    private FiltreMot filtre;

    private final static String WORD = "WORD";
    private final static String WRONG_WORD = "WORD_2";

    @Before
    public void initialiseTests() {
        this.filtre = new FiltreMot(WORD);
    }

    @Test
    public void verifieImplements() {
        assertThat(filtre, instanceOf(IFiltre.class));
    }

    @Test
    public void verifieObjet() {
        assertThat(filtre.getObject(), allOf(
            not(nullValue()),
            instanceOf(String.class),
            equalTo(WORD),
            not(equalTo(WRONG_WORD))
        ));
    }

    @Test
    public void verifieComparaison() {
        assertThat(true, allOf(
            is(filtre.compare(WORD)),
            not(is(filtre.compare(WRONG_WORD)))
        ));
    }
}