import Utilisateur from './Utilisateur.js';

class Message {
  constructor(emetteur, texte, date) {
    this.emetteur = emetteur;
    this.texte = texte;
    this.date = date;
  }
}

export default Message;
