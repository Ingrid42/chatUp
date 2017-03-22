var express = require('express');
var app = express();
var opener = require('opener');
var port = 3000;

app.use("/", express.static('src'));
app.use("/bootstrap/js", express.static('node_modules/bootstrap/dist/js'));
app.use("/fonts", express.static('node_modules/font-awesome/fonts'));
app.use("/jquery", express.static('node_modules/jquery/dist'));
app.use("/tether", express.static('node_modules/tether/dist'));

app.listen(port, () => {
  console.log('Server listening on port ' + port + '!')
});

opener('http://localhost:' + port + '/');
