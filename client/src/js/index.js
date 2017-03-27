const connexion = function() {
  console.log("Init socket.io");
  var socket = io('http://localhost:4000');
  socket.on('connect', function(){});
  socket.on('event', function(data){});
  socket.on('disconnect', function(){});
}

// $(document).ready(connexion);
