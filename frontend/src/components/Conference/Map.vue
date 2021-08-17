<template>
  <div id="map">

  </div>
</template>

<script>
import { reactive, onMounted, onUnmounted } from 'vue'
import SockJS from 'sockjs-client'
import Stomp from 'stomp-websocket'

export default {
  setup() {
    const state = reactive ({
      stompClient: null,
      markerList: [],
      mapPosition: { lat: 37.564214, lng: 127.001699 },
      mapZoom: 10,
    })
    const google = window.google
    var map
    function initMap() {
      map = new google.maps.Map(document.getElementById('map'), {
        center: state.mapPosition,
        zoom: state.mapZoom,
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
      })

      map.addListener('click', function(e) {
        var marker = new google.maps.Marker({
          position: e.latLng,
          map: map,
        });
        sendMarker(marker.position)
        marker.addListener('dblclick', () => {
          marker.setMap(null)
        })
        marker.addListener('click', function() {
          map.setZoom(14);
          map.setCenter(marker.getPosition())
        })
      });
    }
    const connect = () => {
      const socket = new SockJS('https://i5d201.p.ssafy.io:8443/websocket');
      state.stompClient = Stomp.over(socket);
      state.stompClient.connect({}, function () {
        state.stompClient.subscribe(`/topic/chat/${state.conferneceNo}`, function (marker) {
          console.log('marker: ' + marker)
          showMarker(JSON.parse(marker.body))
        });
      });
    }
    const disconnect = () => {
      if (state.stompClient !== null) {
        state.stompClient.disconnect();
      }
    }
    const sendMarker = (position) => {
      state.stompClient.send(`/app/chat/${state.conferneceNo}`, {}, JSON.stringify({'position': position}));
    }

    function showMarker(marker) {
      state.markerList.push(marker)
    }
    onMounted (() => {
      connect()
      initMap()
    })

    onUnmounted (() => {
      disconnect()
    })

    return { state, onMounted, map, connect, disconnect, showMarker, sendMarker }
  },
}
</script>

<style>
#map {
  width: 100%;
  height: 50%;
}
</style>