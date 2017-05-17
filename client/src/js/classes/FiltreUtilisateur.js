class FiltreUtilisateur {
  constructor() {
    this.utilisateurs = [];
  }

  toString() {
    return this.utilisateurs.map((utilisateur, indice) => {
      return utilisateur + ', ';
    });
  }
}

export default FiltreUtilisateur;
