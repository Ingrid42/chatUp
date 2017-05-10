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
    $('.switchToConversationCreation').on('click', () => this.switchToConversationCreation());
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

  switchToConversationCreation() {
    this.hide();
    $('#pageConversationCreation').removeClass('hidden');
    $('#navbarConversation').removeClass('hidden');
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

  generateContactList(utilisateurs) {
    var tag = $('#contactList');
    tag.html('<div class="contact-case"></div> <!-- ligne en haut -->');
    utilisateurs.map((utilisateur, indice) => {
      tag.append(this._contactTemplate(utilisateur));
    });
  }

  generateCreationDiscussionContactList(utilisateurs) {
    var tag = $('#createConvContactList');
    tag.html('');
    utilisateurs.map((utilisateur, indice) => {
      tag.append(this._createConvContactTemplate(utilisateur));
    });
  }

  _contactTemplate(utilisateur) {
    return '\
    <div class="contact-case" data-pseudonyme="' + utilisateur.pseudonyme + '" data-nom="' + utilisateur.nom + '" data-prenom="' + utilisateur.prenom + '">\n\
      <div class="contact vcenter pull-left">\n\
        <i class="fa fa-user contact-icon pull-left" aria-hidden="true"></i> <!-- Photo du contact -->\n\
        <div class="contact-name">' + utilisateur.prenom + '</div> <!-- Nom du contact -->\n\
      </div>\n\
      <div class="pull-right">\n\
        <button type="button" class="btn btn-secondary text-center button-icon switchToConversationAudio" >\n\
          <i class="fa fa-phone fa-2x" aria-hidden="true"></i>\n\
        </button>\n\
        <button type="button" class="btn btn-secondary text-center button-icon switchToConversationTextuelle" >\n\
          <i class="fa fa-envelope-o fa-2x" aria-hidden="true"></i>\n\
        </button>\n\
      </div>\n\
    </div>';
  }

  _createConvContactTemplate(utilisateur) {
    return = '<option value="' + utilisateur.pseudonyme + '">' + utilisateur.prenom + '</option>';
  }
}
export default Navigateur;
