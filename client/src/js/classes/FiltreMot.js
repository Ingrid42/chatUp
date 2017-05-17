class FiltreMot {
  constructor() {
    this.mots = [];
  }

  toString() {
    let string = "";
    for (var i = 0; i < this.mots.length; i++) {
      string += this.mots[i] + " ";
    }
    return string;
  }
}

export default FiltreMot;
