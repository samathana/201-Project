<!DOCTYPE html>
<html>
<head>
<title>Map</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style>
#map {
	height: 65em;
}

.popup-image {
	max-width: 100px; 
	max-height: 100px;
	height: auto; 
}

.my-control {
  background: #fff;
  padding: 5px;
  width: 80px;
}

#list, #post {
    width: 80px;
}

.leaflet-bar {
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.65);
  border-radius: 5px;
}

</style>
<link rel="stylesheet"
	href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"
	integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY="
	crossorigin="" />
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"
	integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo="
	crossorigin=""></script>
</head>
<body>
	<div id="map"></div>
	<script>
        var map = L.map('map', {
            zoomSnap: 0.1
        }).setView([34.0225, -118.285], 16.4);
   		
        L.Control.MyControl = L.Control.extend({
        	  onAdd: function(map) {
        	    var el = L.DomUtil.create('div', 'leaflet-bar my-control');

        	    el.innerHTML = '<a href="list.html" id="list">List View</a>';

        	    return el;
        	  },

        	  onRemove: function(map) {
        	    // Nothing to do here
        	  }
        	});

        	L.control.myControl = function(opts) {
        	  return new L.Control.MyControl(opts);
        	}

        	L.control.myControl({
        	  position: 'topright'
        	}).addTo(map);
        	
        L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(map);
        
        L.Control.plus = L.Control.extend({
      	  onAdd: function(map) {
      	    var el = L.DomUtil.create('div', 'leaflet-bar my-control');

      	    el.innerHTML = '<a href="post.html" id="post">New Post</a>';

      	    return el;
      	  },

      	  onRemove: function(map) {
      	    // Nothing to do here
      	  }
      	});

      	L.control.Plus = function(opts) {
      	  return new L.Control.plus(opts);
      	}

      	L.control.Plus({
      	  position: 'bottomright'
      	}).addTo(map);
      	
      L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
          attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
      }).addTo(map);

        fetch('Map')
        .then(response => response.json())
        .then(data => {
            console.log(data);
            data.forEach(coord => {
                var marker = L.marker([coord.longitude, coord.latitude]).addTo(map);
                marker.bindPopup("<a href='ViewPost.html' onclick='SetSession(" + coord.id + ")'><img class='popup-image' src='" + coord.image + "'></a>");
            });
        });
        
        function SetSession(id) {
        	sessionStorage.setItem("EntryID", id);
        }
    </script>
</body>
</html>
