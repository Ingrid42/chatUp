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
  $('image-input').on('click', function() {

  });

  $();
}

$(document).ready(init)
