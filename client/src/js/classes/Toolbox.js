class Toolbox {
  constructor() {
  }

  importFiles() {
    var links = $('link[rel="import"]');
    var content;
    var el;

    for (var link of links) {
      content = link.import;
      el = $(content).find(".component")[0];
      $("#import").append(el.cloneNode(true));
    }
  }
}

export default Toolbox;
