import Utilisateur from './Utilisateur.js';

class Message {
  constructor(emetteur, texte, date, utilisateurs) {
    this.emetteur = emetteur;
    this.texte = texte;
    this.date = date;
    this.utilisateurs = utilisateurs;
  }

  getLocaleDate() {
    var date = new Date(this.date);
    return date.toLocaleString();
  }
}

export default Message;
