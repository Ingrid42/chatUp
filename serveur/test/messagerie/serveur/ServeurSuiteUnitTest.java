package test.messagerie.serveur;

import test.messagerie.serveur.discussion.DiscussionSuiteUnitTest;
import test.messagerie.serveur.filtre.FiltreSuiteUnitTest;
import test.messagerie.serveur.utilisateur.UtilisateurSuiteUnitTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   ApplicationUnitTest.class,
   RequestDecoderUnitTest.class,
   ResponseEncoderUnitTest.class,
   ServeurUnitTest.class,
   SessionUnitTest.class,
   DiscussionSuiteUnitTest.class,
   FiltreSuiteUnitTest.class,
   UtilisateurSuiteUnitTest.class
})

public class ServeurSuiteUnitTest {   
} 