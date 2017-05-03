import Session from "./classes/Session.js"
import Toolbox from "./classes/Toolbox.js";

const init = function() {
  var toolbox = new Toolbox();
  var session = new Session('ws://localhost:4000');
  toolbox.importFiles();
  onEvent(session);
};

const hide = function() {
  $('div[id^="page"]').addClass('hidden');
  $('div[id^="navbar"]').addClass('hidden');
};

const onEvent = function(session) {
  inscriptionUtilisateur(session);
  connexionUtilisateur(session);

  $('#switchToConnexion').on('click', function() {
    hide();
    $('#pageConnexion').removeClass('hidden');
    $('#navbarAccueil').removeClass('hidden');
  });

  $('#switchToInscription').on('click', function() {
    hide();
    $('#pageInscription').removeClass('hidden');
    $('#navbarAccueil').removeClass('hidden');
  });

  $('.switchToParameters').on('click', function() {
    hide();
    $('#pageParametres').removeClass('hidden');
    $('#navbarParametres').removeClass('hidden');
  });

  $('.switchToContacts').on('click', function() {
    hide();
    $('#pageContacts').removeClass('hidden');
    $('#navbarContacts').removeClass('hidden');
  });

  $('.switchToMessagerie').on('click', function() {
    hide();
    $('#pageMessagerie').removeClass('hidden');
    $('#navbarMessagerie').removeClass('hidden');
  });

  $('.switchToConversationTextuelle').on('click', function() {
    console.log("swicth to conv");
    hide();
    $('#pageConversation').removeClass('hidden');
    $('#navbarConversation').removeClass('hidden');
  });

  $('.switchToConversationAudio').on('click', function() {
    console.log("swicth to conv");
    hide();
    $('#pageConversationAudio').removeClass('hidden');
    $('#navbarConversation').removeClass('hidden');
  })
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
  });
};

$(document).ready(init)
