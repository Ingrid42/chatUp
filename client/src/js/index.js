import Session from "./classes/Session.js"
import Toolbox from "./classes/Toolbox.js";

const init = function() {
  var toolbox = new Toolbox();
  // var session = new Session('localhost:4000');
  toolbox.importFiles();

  onEvent();
  // session.initConnexion();
}

const hide = function() {
  $('div[id^="page"]').addClass('hidden');
  $('div[id^="navbar"]').addClass('hidden');
}

const onEvent = function() {
  $('#image-input').on('click', function() {
  });

  $('#switchToConnexion').on('click', function() {
    hide();
    $('#pageMessagerie').removeClass('hidden');
    $('#navbarMessagerie').removeClass('hidden');
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

  $('.messagerie').on('click', function() {
    console.log("swicth to conv");
    hide();
    $('#pageConversation').removeClass('hidden');
    $('#navbarConversation').removeClass('hidden');
  })
}

$(document).ready(init)
