import Session from "./classes/Session.js"
import Toolbox from "./classes/Toolbox.js";
import Utilisateur from "./classes/Utilisateur.js";

const init = function () {
  var toolbox = new Toolbox();
  var session = new Session();
  var utilisateur = new Utilisateur("ttheologien", "Théologien", "Thibault", "thibault.theologien@insa-rouen.fr", "04/12/1995", "none");
  toolbox.importFiles();
}

$(document).ready(init)
