import Utilisateur from './Utilisateur.js';

class Message {
  constructor(emetteur, texte, date) {
    this.emetteur = emetteur;
    this.texte = texte;
    this.date = date;
  }

  getJSON() {
    return {
      "emetteur": this.emetteur,
      "texte": this.texte,
      "date": this.date
    }
  }
}

export default Message;
