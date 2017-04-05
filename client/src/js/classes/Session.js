import Utilisateur from './Utilisateur.js';
require('socket.io-client');

class Session {
  constructor(IPServer) {
    this.socket = new WebSocket(IPServer);
    this.socket.onopen = () => {
      console.log(this.socket.readyState);
      this.socket.send("test");
    }
    this.socket.onmessage = this.message;
    /*this.socket.addEventListener('emit', () => {
      this.socket.send("test");
    });*/

    this.utilisateur = null;
    this.utilisateurs = [];
  }

  message(evt) {
    console.log(evt);
  }

  initConnexion() {
      /*this.socket.on('connexion_reponse', this._onConnexion);
      this.socket.on('creer_utilisateur_reponse', this._onCreerUtilisateur);
      this.socket.on('creer_discussion_reponse', this._onCreerDiscussion);
      this.socket.on('envoyer_message_reponse', this._onEnvoyerMessage);
      this.socket.on('get_utilisateurs_reponse', this._onGetUtilisateurs);
      this.socket.on('modifier_profil_reponse', this._onModifierProfil);
      this.socket.on('get_profil_reponse', this._onGetProfil);
      this.socket.on('add_filtre_mot_reponse', this._onAddFiltreMot);
      this.socket.on('add_filtre_utilisateur_reponse', this._onAddFiltreUtilisateur);
      this.socket.on('set_controle_parental_reponse', this._onSetControleParental);*/
      console.log("Connection OK");

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

  _onConnexions(data) {
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
