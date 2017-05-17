class Navigateur {
  constructor() {
    this.utilisateur = undefined;
  }

  listen(session) {
    $('#connexionUtilisateur').on('click', () => this.connexionUtilisateur(session));
    $('#inscriptionUtilisateur').on('click', () => this.inscriptionUtilisateur(session));
    $('#creationDiscussion').on('click', () => this.creationDiscussion(session, $('#createConvContactList').val()));
    $('#envoyer_message_bouton').on('click', () => this.envoyerMessage(session));
    $('#enregistrerParametres').on('click', () => this.enregistrerParametres(session));
    $('.deconnexionUtilisateur').on('click', () => this.deconnexionUtilisateur(session));
    $('#setControleParental').on('change', (evt) => this.changerControleParental(session, evt.target));
    $('#filtresMot').on('change', (evt) => this.setFiltresMot(session, evt.target));
    $('#filtresUtilisateur').on('change', (evt) => this.setFiltresUtilisateur(session, evt.target));

    $('#switchToInscription').on('click', () => this.switchToInscription());
    $('#switchToMotDePasse').on('click', () => this.switchToMotDePasse());
    $('.switchToConnexion').on('click', () =>  this.switchToConnexion(session));
    $('.switchToMessagerie').on('click', () => this.getDiscussions(session));
    $('.switchToParameters').on('click', () => this.switchToParameters(session));
    $('.switchToContacts').on('click', () => this.switchToContacts());
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

  switchToMotDePasse() {
    this.hide();
    $('#pageMotDePasse').removeClass('hidden');
    $('#navbarAccueil').removeClass('hidden');
  };

  switchToParameters(session) {
    this.hide();
    this._setCourrielInParametres();
    $('#pageParametres').removeClass('hidden');
    $('#navbarParametres').removeClass('hidden');
    session.send({
      action: "get_filtres_utilisateur",
      contenu : {}
    });
    session.send({
      action: "get_filtres_mot",
      contenu : {}
    });
    session.send({
      action: "get_controle_parental",
      contenu : {}
    });
    session.send
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

  enregistrerParametres(session) {
    let courriel = $('#changerCourriel').val();
    let motDePasse = $('#changerMotDePasse').val();
    console.log(courriel);
    console.log(motDePasse);
  }

  changerControleParental(session, target) {
    let value = $(target).is(":checked");
    let action = 'desactiver_controle_parental';
    if (value) {
      action = 'set_controle_parental';
    }
    let message = {
      action,
      contenu: {
        mot_de_passe_parental: null
      }
    }
    session.send(message);
  }

  setFiltresMot(session, target) {
    let contenu = $(target).val();
    contenu.split(',').map((mot, indice) => {
      if (mot.trim().length > 0) {
        session.send({
          action: "add_filtre_mot",
          contenu: {
            mot_de_passe_parental : null,
        		mot : mot.trim()
          }
        });
      }
    });
  }

  setFiltresUtilisateur(session, target) {
    let contenu = $(target).val();
    contenu.split(',').map((utilisateur, indice) => {
      if (utilisateur.trim().length > 0) {
        session.send({
          action: "add_filtre_utilisateur",
          contenu: {
            mot_de_passe_parental : null,
        		utilisateur : utilisateur.trim()
          }
        });
      }
    });
  }


// FONCTIONS DE GÉNÉRATION
  generateContactList(utilisateurs, session) {
    let tag = $('#contactList');
    tag.html('<div class="contact-case"></div> <!-- ligne en haut -->');
    for (var i = 0; i < utilisateurs.length; i++) {
      tag.append(this._contactTemplate(utilisateurs[i]));
    }
    $('.creationDiscussionFromContactList').on('click', (evt) => this.creationDiscussion(session, [$(evt.target).data('pseudonyme')]));
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

  addMessageInDiscussion(message) {
    var tagContent = $('#conversation_textuelle_contenu');
    if (message.emetteur === this.utilisateur.pseudonyme) {
      tagContent.append(this._createConvMessageReplyTemplate(message));
    }
    else {
      tagContent.append(this._createConvMessageTemplate(message));
    }
    tagContent.scrollTop(tagContent[0].scrollHeight);
  }

  completeFiltresUtilisateur(filtresUtilisateur) {
     $('#filtresUtilisateur').val(filtresUtilisateur.toString());
  }

  completeFiltresMot(filtresMot) {
    $('#filtresMot').val(filtresMot.toString());
  }

  setControleParental(isTrue) {
    $('#setControleParental');
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
        <button type="button" class="btn btn-secondary text-center button-icon creationDiscussionFromContactList" data-pseudonyme="' + utilisateur.pseudonyme + '">\n\
          <i class="fa fa-envelope-o fa-2x" aria-hidden="true" data-pseudonyme="' + utilisateur.pseudonyme + '"></i>\n\
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

  _setCourrielInParametres() {
    $('#changerCourriel').val(this.utilisateur.adresseMel);
  }

// AUTRES FONCTIONS
  hide() {
    $('div[id^="page"]').addClass('hidden');
    $('div[id^="navbar"]').addClass('hidden');
  };
}


export default Navigateur;
