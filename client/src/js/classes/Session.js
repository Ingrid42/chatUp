import Utilisateur from './Utilisateur.js';
import DiscussionTexte from './DiscussionTexte.js';
import FiltreMot from './FiltreMot.js';
import FiltreUtilisateur from './FiltreUtilisateur.js';
import Message from './Message.js';
import Navigateur from './Navigateur.js';

require('socket.io-client');

class Session {
  constructor(IPServer, navigateur) {
    this.IPServer = IPServer;
    this.utilisateur = null;
    this.utilisateurs = [];
    this.discussionsTextes = [];
    this.navigateur = navigateur;
    this.filtreMot = new FiltreMot();
    this.filtreUtilisateur = new FiltreUtilisateur();
    this.initConnexion();
  }

  initConnexion() {
    this.socket = new WebSocket(this.IPServer);
    this.socket.onopen = () => console.log('Connexion au serveur ouverte');
    this.socket.onmessage = (response) => this.message(response);
  }

  send(message) {
    this.socket.send(JSON.stringify(message));
  }

  deconnexion() {
    console.log('Connexion au serveur close');
    this.socket.close();
    this.initConnexion();
    this.navigateur.switchToConnexion();
  }

  message(response) {
    var responseJSON = JSON.parse(response.data);
    console.log(response);
    if (responseJSON.etat !== false) {
      switch (responseJSON.action) {
        case 'connexion_reponse':
          this._onConnexion(responseJSON.contenu);
          break;
        case 'creer_utilisateur_reponse':
          this._onCreerUtilisateur(responseJSON.contenu);
          break;
        case 'creer_discussion_reponse':
          this._onCreerDiscussion(responseJSON.contenu);
          break;
        case 'envoyer_message_reponse':
          this._onEnvoyerMessage(responseJSON.contenu);
          break;
        case 'get_utilisateurs_reponse':
          this._onGetUtilisateurs(responseJSON.contenu);
          break;
        case 'modifier_profil_reponse':
          this._onModifierProfil(responseJSON.contenu);
          break;
        case 'get_profil_reponse':
          this._onGetProfil(responseJSON.contenu);
          break;
        case 'add_filtre_mot_reponse':
          this._onAddFiltreMot(responseJSON.contenu);
          break;
        case 'add_filtre_utilisateur_reponse':
          this._onAddFiltreUtilisateur(responseJSON.contenu);
          break;
        case 'get_filtre_mot_reponse':
          this._onGetFiltresMot(responseJSON.contenu);
          break;
        case 'get_filtre_utilisateur_reponse':
          this._onGetFiltresUtilisateur(responseJSON.contenu);
          break;
        case 'get_controle_parental_reponse':
          this._onGetControleParental(responseJSON.contenu);
          break;
        case 'set_controle_parental_reponse':
          this._onSetControleParental(responseJSON.contenu);
          break;
        case 'get_discussion_reponse':
          this._onGetDiscussion(responseJSON.contenu);
          break;
        case 'get_discussions_reponse':
          this._onGetDiscussions(responseJSON.contenu);
          break;
        case 'message_reponse':
          this._onMessage(responseJSON.contenu);
          break;
      }
    }
    else {
      console.log("Error from server");
    }
  }

  _initUtilisateur(data) {
    this.utilisateur = new Utilisateur(
      data.pseudonyme,
      data.nom,
      data.prenom,
      data.adresse_mel,
      data.date_naissance,
      data.photo
    );
    this.navigateur.utilisateur = this.utilisateur;
  }

  _onConnexion(data) {
    this._initUtilisateur(data);
    this.send({
      action: "get_discussions",
      contenu: {}
    });
  }

  _onCreerUtilisateur(data) {
    this._initUtilisateur(data);
    this.navigateur.switchToConnexion();
  }

  _onCreerDiscussion(data) {
    this._createDiscussion(data);
    let message = {
    	action : "get_discussion",
    	contenu : {
    		id_discussion : data.id
    	}
    }
    this.send(message);
  }

  _onGetDiscussion(data) {
    this._saveMessagesToDiscussion(data.id, data.messages);
    this.navigateur.generateConversationTextuelle(this._getDiscussion(data.id));
    this.navigateur.switchToConversationTextuelle();
  }

  _onGetDiscussions(data) {
    this._saveDiscussions(data);
    this.navigateur.generateMessagerie(this.discussionsTextes, this);
    this.navigateur.switchToMessagerie(this);
    this.send({
      action: "get_utilisateurs",
      contenu: {}
    });
  }

  _onEnvoyerMessage(data) {
    // console.log(data);
  }

  _onGetUtilisateurs(data) {
    this._saveUtilisateurs(data);
    this.navigateur.generateContactList(this.utilisateurs, this);
    this.navigateur.generateCreationDiscussionContactList(this.utilisateurs);
  }

  _onModifierProfil(data) {

  }

  _onGetProfil(data) {

  }

  _onAddFiltreMot(data) {

  }

  _onAddFiltreUtilisateur(data) {

  }

  _onSetControleParental(data) {

  }

  _onGetFiltresMot(data) {
    for (var i = 0; i < data.filtres.length; i++) {
      if (!this._motEstFiltre(data.filtres[i])) {
        this.filtreMot.mots.push(data.filtres[i]);
      }
    }
    this.navigateur.completeFiltresMot(this.filtreMot);
  }

  _onGetFiltresUtilisateur(data) {
    for (var i = 0; i < data.filtres.length; i++) {
      if (!this._motEstFiltre(data.filtres[i])) {
        this.filtreUtilisateur.mots.push(data.filtres[i]);
      }
    }
    this.navigateur.completeFiltresUtilisateur(this.filtreUtilisateur);
  }

  _onGetControleParental(data) {
    // console.log("getControleParental");
    // console.log(data.controle_parental);
    this.navigateur.setControleParental(data.controle_parental);
  }

  _onMessage(data) {
    let discussion = this._getDiscussion(data.id_discussion);
    if (discussion != null) {
      let message = new Message(data.pseudonyme, data.message, data.date, discussion.utilisateurs);
      discussion.messages.push(message);
      this.navigateur.addMessageInDiscussion(message);
    }
  }

  _saveDiscussions(data) {
    let discussion;
    for (var i=0; i < data.discussions.length; i++) {
      let estPresent = false;
      discussion = new DiscussionTexte(
        data.discussions[i].id,
        data.discussions[i].utilisateurs,
      );
      for (var j=0; j < this.discussionsTextes.length; j++) {
        if (this.discussionsTextes[j].id == discussion.id) {
          estPresent = true;
        }
      }
      if (!estPresent) {
        this.discussionsTextes.push(discussion)
      }
    }
  }

  _saveUtilisateurs(data) {
    let utilisateur;
    for (var i=0; i < data.utilisateurs.length; i++) {
      let estPresent = false;
      utilisateur = new Utilisateur(
        data.utilisateurs[i].pseudonyme,
        data.utilisateurs[i].nom,
        data.utilisateurs[i].prenom,
        undefined,
        undefined,
        undefined
      );
      for (var j=0; j < this.utilisateurs.length; j++) {
        if (this.utilisateurs[j].pseudonyme === utilisateur.pseudonyme) {
          estPresent = true;
        }
      }
      if (!estPresent) {
        this.utilisateurs.push(utilisateur);
      }
    }
  }

  _saveMessagesToDiscussion(id, messages) {
    let message;
    let discussion = this._getDiscussion(id);
    if (discussion !== null) {
      for (var i = discussion.messages.length; i < messages.length; i++) {
        message = new Message(
          messages[i].pseudonyme,
          messages[i].message,
          messages[i].date,
          discussion.utilisateurs
        );
        discussion.messages.push(message);
      }
    }
  }

  _createDiscussion(data) {
    let utilisateurs = [];
    let utilisateur;
    if (this._getDiscussion(data.id) === null) {
      for (var i = 0; i < data.utilisateurs.length; i++) {
        utilisateur = this._getUtilisateur(data.utilisateurs[i].pseudonyme);
        if (utilisateur != null) {
          utilisateurs.push(utilisateur)
        }
      }
      this.discussionsTextes.push(new DiscussionTexte(data.id, utilisateurs));
    }
  }

  _getDiscussion(id) {
    for (var i = 0; i < this.discussionsTextes.length; i++) {
      if (this.discussionsTextes[i].id == id) {
        return this.discussionsTextes[i];
      }
    }
    return null;
  }

  _getUtilisateur(pseudonyme) {
    for (var i = 0; i < this.utilisateurs.length; i++) {
      if (this.utilisateurs[i].pseudonyme == pseudonyme) {
        return this.utilisateurs[i];
      }
    }
    return null;
  }

  _utilisateurEstFiltre(pseudonyme) {
    for (var i = 0; i < this.filtresUtilisateur.utilisateurs.length; i++) {
      if (this.filtresUtilisateur.utilisateurs[i] == pseudonyme) {
        return true;
      }
    }
    return false;
  }

  _motEstFiltre(mot) {
    for (var i = 0; i < this.filtresMot.mots.length; i++) {
      if (this.filtresMot.mots[i].mot == mot) {
        return true;
      }
    }
    return false;
  }
}

export default Session;
