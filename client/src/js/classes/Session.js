import Utilisateur from './Utilisateur.js';
require('socket.io-client');

class Session {
  constructor(IPServer) {
    this.socket = new WebSocket(IPServer);
    this.socket.onopen = () => {
      console.log('Connexion au serveur ok');
    }
    this.socket.onmessage = this.message;
    this.utilisateur = null;
    this.utilisateurs = [];
  }

  send(message) {
    this.socket.send(JSON.stringify(message));
  }

  message(response) {
    console.log(response);
    switch (response.action) {
      case 'connexion_reponse':
        this._onConnexion(response.contenu);
        break;
      case 'creer_utilisateur_reponse':
        this._onCreerUtilisateur(response.contenu);
        break;
      case 'creer_discussion_reponse':
        this._onCreerDiscussion(response.contenu);
        break;
      case 'envoyer_message_reponse':
        this._onEnvoyerMessage(response.contenu);
        break;
      case 'get_utilisateurs_reponse':
        this._onGetUtilisateurs(response.contenu);
        break;
      case 'modifier_profil_reponse':
        this._onModifierProfil(response.contenu);
        break;
      case 'get_profil_reponse':
        this._onGetProfil(response.contenu);
        break;
      case 'add_filtre_mot_reponse':
        this._onAddFiltreMot(response.contenu);
        break;
      case 'add_filtre_utilisateur_reponse':
        this._onAddFiltreUtilisateur(response.contenu);
        break;
      case 'set_controle_parental_reponse':
        this._onSetControleParental(response.contenu);
        break;
      default:

    }
  }

  _initUtilisateur(data) {
    this.utilisateur = new Utilisateur(
      data.pseudonyme,
      data.nom,
      data.prenom,
      data.adresseMel,
      data.dateNaissance,
      data.photo
    );
    console.log(this.utilisateur);
  }

  _onConnexion(data) {
    this._initUtilisateur(data);
  }

  _onCreerUtilisateur(data) {
    this._initUtilisateur(data);
  }

  _onCreerDiscussion(data) {

  }

  _onEnvoyerMessage(data) {

  }

  _onGetUtilisateurs(data) {

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

}

export default Session;
