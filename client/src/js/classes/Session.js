import Utilisateur from './Utilisateur.js';
import Navigateur from './Navigateur.js';
require('socket.io-client');

class Session {
  constructor(IPServer, navigateur) {
    this.socket = new WebSocket(IPServer);
    this.socket.onopen = () => console.log('Connexion au serveur ok');
    this.socket.onmessage = (response) => this.message(response);
    this.utilisateur = null;
    this.utilisateurs = [];
    this.navigateur = navigateur;
  }

  send(message) {
    this.socket.send(JSON.stringify(message));
  }

  message(response) {
    var responseJSON = JSON.parse(response.data);
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
        case 'set_controle_parental_reponse':
          this._onSetControleParental(responseJSON.contenu);
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
  }

   _onConnexion(data) {
    this._initUtilisateur(data);
    this.navigateur.switchToMessagerie();
  }

  _onCreerUtilisateur(data) {
    this._initUtilisateur(data);
    this.navigateur.switchToConnexion();
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
