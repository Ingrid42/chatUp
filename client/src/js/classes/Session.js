import Utilisateur from './Utilisateur.js';

class Session {
  constructor() {
    this.socket = require('socket.io-client');
    this.IPServer = 'http://localhost:4000';
    this.utilisateur = null;
    this.utilisateurs = [];
  }

  initConnexion() {
    this.socket(this.IPServer);
    this.socket.on('connexion_reponse', _onConnexion);
    this.socket.on('creer_utilisateur_reponse', _onCreerUtilisateur);
    this.socket.on('creer_discussion_reponse', _onCreerDiscussion);
    this.socket.on('envoyer_message_reponse', _onEnvoyerMessage);
    this.socket.on('get_utilisateurs_reponse', _onGetUtilisateurs);
    this.socket.on('modifier_profil_reponse', _onModifierProfil);
    this.socket.on('get_profil_reponse', _onGetProfil);
    this.socket.on('add_filtre_mot_reponse', _onAddFiltreMot);
    this.socket.on('add_filtre_utilisateur_reponse', _onAddFiltreUtilisateur);
    this.socket.on('set_controle_parental_reponse', _onSetControleParental);
  }

  emit(message, data) {
    this.socket.emit(message, data);
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
