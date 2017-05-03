class Navigateur {
  constructor() {
  }

  listen(session) {
    $('#connexionUtilisateur').on('click', () => this.connexionUtilisateur(session));
    $('#inscriptionUtilisateur').on('click', () => this.inscriptionUtilisateur(session));
    $('#switchToConnexion').on('click', () =>  this.switchToConnexion());
    $('#switchToInscription').on('click', () => this.switchToInscription());
    $('.switchToParameters').on('click', () => this.switchToParameters());
    $('.switchToContacts').on('click', () => this.switchToContacts());
    $('.switchToMessagerie').on('click', () => this.switchToMessagerie());
    $('.switchToConversationTextuelle').on('click', () => this.switchToConversationTextuelle());
    $('.switchToConversationAudio').on('click', () => this.switchToConversationAudio());
  };

  connexionUtilisateur(session) {
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
  };


  inscriptionUtilisateur(session) {
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
  };

  switchToConnexion() {
    this.hide();
    $('#pageConnexion').removeClass('hidden');
    $('#navbarAccueil').removeClass('hidden');
  };

  switchToInscription() {
    this.hide();
    $('#pageInscription').removeClass('hidden');
    $('#navbarAccueil').removeClass('hidden');
  };

  switchToParameters() {
    this.hide();
    $('#pageParametres').removeClass('hidden');
    $('#navbarParametres').removeClass('hidden');
  };

  switchToContacts() {
    this.hide();
    $('#pageContacts').removeClass('hidden');
    $('#navbarContacts').removeClass('hidden');
  };

  switchToMessagerie() {
    this.hide();
    $('#pageMessagerie').removeClass('hidden');
    $('#navbarMessagerie').removeClass('hidden');
  };

  switchToConversationTextuelle() {
    this.hide();
    $('#pageConversation').removeClass('hidden');
    $('#navbarConversation').removeClass('hidden');
  };

  switchToConversationAudio() {
    this.hide();
    $('#pageConversationAudio').removeClass('hidden');
    $('#navbarConversation').removeClass('hidden');
  };

  hide() {
    $('div[id^="page"]').addClass('hidden');
    $('div[id^="navbar"]').addClass('hidden');
  };
}

export default Navigateur;
