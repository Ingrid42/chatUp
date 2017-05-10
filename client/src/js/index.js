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

const inscriptionUtilisateur = function(session) {

  $('#inscriptionUtilisateur').on('click', function() {
    verification();
    let pseudonyme = $('#inputInscriptionPseudo').val();
    let prenom = $('#inputInscriptionPrenom').val();
    let mot_de_passe_confirmation = $('#inputInscriptionPasswordConfirmation').val();

    let nom = $('#inputInscriptionNom').val();
    let mot_de_passe = $('#inputInscriptionPassword').val();
    let adresse_mel = $('#inputInscriptionEmail').val();
    let date_naissance = $('#inputInscriptionDatePicker').val();

    let message = {
      contenu : {
      action : "creer_utilisateur",
        pseudonyme,
        nom,
        mot_de_passe,
        prenom,
        adresse_mel,
        date_naissance
      }
    };
    switchToConnexion();
    session.send(message);
  });

};
const verification = function() {

  var emailBox = document.getElementById("inputInscriptionEmail");
  var pseudo = document.getElementById("inputInscriptionPseudo");
  var error = document.getElementById("msgError");
  if(emailBox.value=="") {
    if(!error.hasChildNodes()) {
      emailBox.style.outline="solid Red";
    }
      error.appendChild(document.createTextNode("Champ obligatoire"));
  } else {
    document.forms['formInscription'].submit();
  }
};

