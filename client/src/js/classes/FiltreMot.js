class FiltreMot {
  constructor() {
    this.mots = [];
  }

  toString() {
    return this.mots.map((mot, indice) => {
      return mot + ', ';
    });
  }
}

export default FiltreMot;
