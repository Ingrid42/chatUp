import FiltreMot from "./FiltreMot.js";
import FiltreUtilisateur from "./FiltreUtilisateur.js";
import Discussion from "./Discussion.js";

class Utilisateur {
  constructor(pseudonyme, nom, prenom, adresseMel, dateNaissance, photo) {
    this.pseudonyme = pseudonyme;
    this.nom = nom;
    this.prenom = prenom;
    this.adresseMel = adresseMel;
    this.dateNaissance = dateNaissance;
    this.photo = photo;
    this.filtreMot = new FiltreMot();
    this.filtreUtilisateur = new FiltreUtilisateur();
    this.discussion = [];
  }
}

export default Utilisateur;
