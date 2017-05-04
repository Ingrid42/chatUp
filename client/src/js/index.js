import Session from "./classes/Session.js"
import Toolbox from "./classes/Toolbox.js";
import Navigateur from "./classes/Navigateur.js";

const init = function() {
  var toolbox = new Toolbox();
  var navigateur = new Navigateur();
  var session = new Session('ws://localhost:4000', navigateur);
  toolbox.importFiles();
  navigateur.listen(session);
  // onEvent(session);
};

const hide = function() {
  $('div[id^="page"]').addClass('hidden');
  $('div[id^="navbar"]').addClass('hidden');
};

const onEvent = function(session) {
  inscriptionUtilisateur(session);
  connexionUtilisateur(session);

  $('#switchToConnexion').on('click', () =>  switchToConnexion());
  $('#switchToInscription').on('click', () => switchToInscription());
  $('.switchToParameters').on('click', () => switchToParameters());
  $('.switchToContacts').on('click', () => switchToContacts());
  $('.switchToMessagerie').on('click', () => switchToMessagerie());
  $('.switchToConversationCreation').on('click', () => switchToConversationCreation());
  $('.switchToConversationTextuelle').on('click', () => switchToConversationTextuelle());
  $('.switchToConversationAudio').on('click', () => switchToConversationAudio());
};

const connexionUtilisateur = function(session) {
  $('#connexionUtilisateur').on('click', function() {
    let pseudonyme = $('#inputConnexionPseudo').val();
    let mot_de_passe = $('#inputConnexionPassword').val();

    let message = {
      action : "connexion",
      contenu : {
        pseudonyme,
        mot_de_passe
      }
    };
    session.send(message);
    switchToMessagerie();
  });
};


const inscriptionUtilisateur = function(session) {
  $('#inscriptionUtilisateur').on('click', function() {
    let pseudonyme = $('#inputInscriptionPseudo').val();
    let mot_de_passe = $('#inputInscriptionPassword').val();
    let mot_de_passe_confirmation = $('#inputInscriptionPasswordConfirmation').val();
    let nom = $('#inputInscriptionNom').val();
    let prenom = $('#inputInscriptionPrenom').val();
    let adresse_mel = $('#inputInscriptionEmail').val();
    let date_naissance = $('#inputInscriptionDatePicker').val();

    let message = {
    	action : "creer_utilisateur",
    	contenu : {
    		pseudonyme,
        mot_de_passe,
        nom,
        prenom,
        adresse_mel,
        date_naissance
    	}
    };
    session.send(message);
    switchToConnexion();
  });
};

const switchToConnexion = function() {
  hide();
  $('#pageConnexion').removeClass('hidden');
  $('#navbarAccueil').removeClass('hidden');
};

const switchToInscription = function() {
  hide();
  $('#pageInscription').removeClass('hidden');
  $('#navbarAccueil').removeClass('hidden');
};

const switchToParameters = function() {
  hide();
  $('#pageParametres').removeClass('hidden');
  $('#navbarParametres').removeClass('hidden');
};

const switchToContacts = function() {
  hide();
  $('#pageContacts').removeClass('hidden');
  $('#navbarContacts').removeClass('hidden');
};

const switchToMessagerie = function() {
  hide();
  $('#pageMessagerie').removeClass('hidden');
  $('#navbarMessagerie').removeClass('hidden');
};

const switchToConversationCreation = function() {
  hide();
  $('#pageConversationCreation').removeClass('hidden');
  $('#navbarConversation').removeClass('hidden');
};

const switchToConversationTextuelle = function() {
  hide();
  $('#pageConversation').removeClass('hidden');
  $('#navbarConversation').removeClass('hidden');
};

const switchToConversationAudio = function() {
  hide();
  $('#pageConversationAudio').removeClass('hidden');
  $('#navbarConversation').removeClass('hidden');
};

$(document).ready(init)
