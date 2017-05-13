class Navigateur {
  constructor() {
    this.utilisateur = undefined;
  }

  listen(session) {
    $('#connexionUtilisateur').on('click', () => this.connexionUtilisateur(session));
    $('#inscriptionUtilisateur').on('click', () => this.inscriptionUtilisateur(session));
    $('#creationDiscussion').on('click', () => this.creationDiscussion(session, $('#createConvContactList').val()));
    $('#switchToConnexion').on('click', () =>  this.switchToConnexion(session));
    $('#switchToInscription').on('click', () => this.switchToInscription());
    $('#envoyer_message_bouton').on('click', () => this.envoyerMessage(session));
    $('.deconnexionUtilisateur').on('click', () => this.deconnexionUtilisateur(session));
    $('.switchToParameters').on('click', () => this.switchToParameters());
    $('.switchToContacts').on('click', () => this.switchToContacts());
    $('.switchToMessagerie').on('click', () => this.getDiscussions(session));
    $('.switchToConversationCreation').on('click', () => this.switchToConversationCreation());
    $('.switchToConversationTextuelle').on('click', () => this.switchToConversationTextuelle());
    $('.switchToConversationAudio').on('click', () => this.switchToConversationAudio());
  };


// FONCTIONS POUR CHANGER DE PAGE
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



// FONCTIONS COMPORTEMENTALES
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

  creationDiscussion(session, utilisateurs) {
    var message = {
      action : "creer_discussion",
      contenu : {
        utilisateurs
      }
    };

    if (message.contenu.utilisateurs.length > 0) {
      session.send(message);
    }
  }

  deconnexionUtilisateur(session) {
    session.deconnexion();
    this.switchToConnexion();
  }

  envoyerMessage(session) {
    let input = $('#envoyer_message_input');
    let message = {
      action: "envoyer_message",
      contenu: {
        id_discussion: input.data('discussion_id').toString(),
        message: input.val()
      }
    }
    session.send(message);
    input.val('');
  }

  getDiscussion(target, session) {
    let message = {
      action: "get_discussion",
      contenu: {
        id_discussion: $(target).data('disussion_id').toString()
      }
    }
    session.send(message);
  }

  getDiscussions(session) {
    let message = {
      action: "get_discussions",
      contenu: {}
    }
    session.send(message);
  }


// FONCTIONS DE GÉNÉRATION
  generateContactList(utilisateurs, session) {
    var tag = $('#contactList');
    tag.html('<div class="contact-case"></div> <!-- ligne en haut -->');
    utilisateurs.map((utilisateur, indice) => {
      tag.append(this._contactTemplate(utilisateur));
      $('.creationDiscussionFromContactList').on('click', (evt) => this.creationDiscussion(session, [$(evt.target).data('pseudonyme')]));
    });
  }

  generateCreationDiscussionContactList(utilisateurs) {
    var tag = $('#createConvContactList');
    tag.html('');
    utilisateurs.map((utilisateur, indice) => {
      tag.append(this._createConvContactTemplate(utilisateur));
    });
  }

  generateConversationTextuelle(discussion) {
    var tagUsers = $('#conversation_textuelle_utilisateurs');
    var tagContent = $('#conversation_textuelle_contenu');
    var tagInput = $('#envoyer_message_input');
    var nomUtilisateurs = "";
    tagContent.html('');
    tagInput.val('');
    tagInput.data('discussion_id', discussion.id);

    for (var i = 0; i < discussion.utilisateurs.length; i++) {
      nomUtilisateurs += discussion.utilisateurs[i].prenom;
      if (i < discussion.utilisateurs.length - 1)
        nomUtilisateurs += ", ";
    }
    tagUsers.text(nomUtilisateurs);

    discussion.messages.map((message, indice) => {
      if (this.utilisateur.pseudonyme === message.emetteur) {
        tagContent.append(this._createConvMessageReplyTemplate(message));
      }
      else {
        tagContent.append(this._createConvMessageTemplate(message));
      }
    });

  }

  generateMessagerie(discussions, session) {
    var tag = $('#messagerie');
    tag.html('');
    for (var i = 0; i < discussions.length; i++) {
      let discussion = discussions[i];
      tag.append(this._discussionTemplate(discussion));
    }
    $('.getDiscussion').on('click', (evt) => this.getDiscussion(evt.target, session));
  }


// FONCTIONS DE TEMPLATE
  _discussionTemplate(discussion) {
    let nomUtilisateurs = "";
    for (var i = 0; i < discussion.utilisateurs.length; i++) {
      nomUtilisateurs += discussion.utilisateurs[i].prenom;
      if (i < discussion.utilisateurs.length - 1)
        nomUtilisateurs += ", ";
    }
    return '\
    <button data-disussion_id="'+ discussion.id + '" class="btn btn-secondary messagerie getDiscussion" >\n\
      <div data-disussion_id="'+ discussion.id + '" class="vcenter">\n\
        <i data-disussion_id="'+ discussion.id + '" class="fa fa-users contact-icon pull-left" aria-hidden="true"></i> <!-- Photo du contact -->\n\
        <div data-disussion_id="'+ discussion.id + '" class="contact-name">' + nomUtilisateurs + '</div> <!-- Nom du contact -->\n\
        <i data-disussion_id="'+ discussion.id + '" class="fa fa-circle contact-notification pull-right" aria-hidden="true"></i> <!-- pull-right ne fonctionne pas à cause du vcenter de la div -->\n\
      </div>\n\
    </button>';
  }

  _contactTemplate(utilisateur) {
    return '\
    <div class="contact-case">\n\
      <div class="contact vcenter pull-left">\n\
        <i class="fa fa-user contact-icon pull-left" aria-hidden="true"></i> <!-- Photo du contact -->\n\
        <div class="contact-name">' + utilisateur.prenom + '</div> <!-- Nom du contact -->\n\
      </div>\n\
      <div class="pull-right">\n\
        <button type="button" class="btn btn-secondary text-center button-icon switchToConversationAudio" >\n\
          <i class="fa fa-phone fa-2x" aria-hidden="true"></i>\n\
        </button>\n\
        <button type="button" class="btn btn-secondary text-center button-icon creationDiscussionFromContactList" data-pseudonyme="' + utilisateur.pseudonyme + '" data-nom="' + utilisateur.nom + '" data-prenom="' + utilisateur.prenom + '">\n\
          <i class="fa fa-envelope-o fa-2x" aria-hidden="true"></i>\n\
        </button>\n\
      </div>\n\
    </div>';
  }

  _createConvContactTemplate(utilisateur) {
    return '<option value="' + utilisateur.pseudonyme + '">' + utilisateur.prenom + '</option>';
  }

  _createConvMessageTemplate(message) {
    return '\n\
    <div class="conversation-message">\n\
      <div class="message-name">' + message.emetteur + '</div>\n\
      <div class="message-content">' + message.texte + '</div>\n\
      <div class="message-time">' + message.getLocaleDate() + '</div>\n\
    </div>';
  }

  _createConvMessageReplyTemplate(message) {
    return '\n\
      <div class="conversation-message-reply">\n\
        <div class="message-content-reply">' + message.texte + '</div>\n\
        <div class="message-time-reply">' + message.getLocaleDate() + '</div>\n\
      </div>';
  }

// AUTRES FONCTIONS
  hide() {
    $('div[id^="page"]').addClass('hidden');
    $('div[id^="navbar"]').addClass('hidden');
  };
}


export default Navigateur;
