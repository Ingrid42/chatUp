import Session from "./classes/Session.js"
import Toolbox from "./classes/Toolbox.js";

const init = function() {
  var toolbox = new Toolbox();
  // var session = new Session('localhost:4000');
  toolbox.importFiles();

  onEvent();
  // session.initConnexion();
}

const onEvent = function() {
  $('#image-input').on('click', function() {

  });

  $('#switchToConnexion').on('click', function() {
    $('#pageConnexion').addClass('hidden');
    $('#navbar').addClass('hidden');
    $('#pageMessagerie').removeClass('hidden');
    $('#navbarMessagerie').removeClass('hidden');
  });

  $('#switchToInscription').on('click', function() {
    $('#pageConnexion').addClass('hidden');
    $('#pageInscription').removeClass('hidden');
  });

  $('.switchToParameters').on('click', function() {
    $('#pageMessagerie').addClass('hidden');
    $('#navbarMessagerie').addClass('hidden')
    $('#pageParametres').removeClass('hidden');
  });

  $('.switchToContacts').on('click', function() {
    $('#pageMessagerie').addClass('hidden');
    $('#navbarMessagerie').addClass('hidden')
    $('#pageContacts').removeClass('hidden');
    $('#navbarContacts').removeClass('hidden');
  });
}

$(document).ready(init)
