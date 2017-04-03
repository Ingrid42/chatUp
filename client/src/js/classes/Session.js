import Utilisateur from './Utilisateur.js';

class Session {
  constructor() {
    this.socket = require('socket.io-client');
    this.IPServer = 'http://localhost:4000';
    this.utilisateur = null;
  }

  initConnexion() {
    this.socket(this.IPServer);
    this.socket.on('connexion_reponse', _onConnexion);
    this.socket.on('creer_utilisateur_reponse', _onConnexion);
    this.socket.on('creer_discussion_reponse', _onConnexion);
    this.socket.on('envoyer_message_reponse', _onConnexion);
    this.socket.on('get_utilisateurs_reponse', _onConnexion);
    this.socket.on('modifier_profil_reponse', _onConnexion);
    this.socket.on('get_profil_reponse', _onConnexion);
    this.socket.on('add_filtre_mot_reponse', _onConnexion);
    this.socket.on('add_filtre_utilisateur_reponse', _onConnexion);
    this.socket.on('set_controle_parental_reponse', _onConnexion);
  }

  emit(message, data) {
    this.socket.emit(message, data);
  }

  _onConnexion(data) {
    console.log(data);
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

}

export default Session;
