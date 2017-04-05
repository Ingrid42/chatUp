import Session from "./classes/Session.js"
import Toolbox from "./classes/Toolbox.js";

const init = function () {
  var toolbox = new Toolbox();
  var session = new Session('localhost:4000');
  toolbox.importFiles();


  emit(session)

  // session.initConnexion();
}

function emit(session) {
  var message = {
    "message": "yolo"
  }
  console.log("Sleep");
  sleep(2000);
  session.emit("test", message);
}

function sleep( sleepDuration ){
    var now = new Date().getTime();
    while(new Date().getTime() < now + sleepDuration){ /* do nothing */ }
}

$(document).ready(init)
