import Session from "./classes/Session.js"
import Toolbox from "./classes/Toolbox.js";
import Navigateur from "./classes/Navigateur.js";

const init = function() {
  var toolbox = new Toolbox();
  var navigateur = new Navigateur();
  var session = new Session('ws://localhost:4000', navigateur);
  toolbox.importFiles();
  navigateur.listen(session);
};

$(document).ready(init)
