<template>
  <div id="map">

  </div>
</template>

<script>
import { reactive, onMounted } from 'vue'

export default {
  setup() {
    const state = reactive ({

    })
    const google = window.google
    var map
    function initMap() {
      map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 37, lng: 126},
        zoom: 7,
        styles: [{
          featureType: 'poi',
          stylers: [{ visibility: 'on' }]  // Turn off POI.
        },
        {
          featureType: 'transit.station',
          stylers: [{ visibility: 'on' }]  // Turn off bus, train stations etc.
        }],
        disableDoubleClickZoom: true,
        streetViewControl: true,
      });

      map.addListener('click', function(e) {
        var marker = new google.maps.Marker({
          position: e.latLng,
          map: map,
        });
        marker.addListener('dblclick', () => {
          marker.setMap(null)
        })
        marker.addListener('click', function() {
          console.log('클릭')
          map.center = marker.position
          map.zoom = 12
        })
      });
    }
    onMounted (() => {
      initMap()
    })

    return { state, onMounted, map }
  },
}
</script>

<style>
#map {
  width: 100%;
  height: 40%;
}
</style>