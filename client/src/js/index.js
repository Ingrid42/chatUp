import Session from "./classes/Session.js"
import Toolbox from "./classes/Toolbox.js";

const init = function () {
  var toolbox = new Toolbox();
  var session = new Session();
  toolbox.importFiles();
}

$(document).ready(init)
