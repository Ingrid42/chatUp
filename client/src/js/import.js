var links = $('link[rel="import"]');
var content;
var el;

for (var link of links) {
  content = link.import;
  el = $(content).find("#component")[0];
  console.log(el);
  $("#import").append(el.cloneNode(true));
}

// TODO: Ajouter vérification de l'existence des fichiers
