class Utilisateur {
  constructor(pseudonyme, nom, prenom, adresseMel, dateNaissance, photo) {
    this.pseudonyme = pseudonyme;
    this.nom = nom;
    this.prenom = prenom;
    this.adresseMel = adresseMel;
    this.dateNaissance = dateNaissance;
    this.photo = photo;
  }

  getJSON() {
    return {
      "pseudonyme": this.pseudonyme,
      "nom": this.nom,
      "prenom": this.prenom,
      "adresseMel": this.adresseMel,
      "dateNaissance": this.dateNaissance,
      "photo": this.photo
    }
  }
}

export default Utilisateur;
