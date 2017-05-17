class FiltreUtilisateur {
  constructor() {
    this.utilisateurs = [];
  }

  toString() {
    let string = "";
    for (var i = 0; i < this.utilisateurs.length; i++) {
      string += this.utilisateurs[i] + " ";
    }
    return string;
  }
}

export default FiltreUtilisateur;
