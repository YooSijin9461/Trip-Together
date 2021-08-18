<template>
  <div id="map">

  </div>
</template>

<script>
import { reactive, onMounted, onUnmounted, onUpdated, computed } from 'vue'
import SockJS from 'sockjs-client'
import Stomp from 'stomp-websocket'
import { useStore } from 'vuex'

export default {
  setup() {
    const store = useStore()
    const state = reactive ({
      stompClient: null,
      marker: null,
      markerList: [],
      conferneceNo: computed(() => store.getters['getConferenceno']),
      mapPosition: { lat: 37.564214, lng: 127.001699 },
      mapZoom: 10,
    })
    const google = window.google
    var map;
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

      map.addListener('click', function({ latLng }) {
        sendMarker(latLng)
        })
        map.addListener('rightclick', function({latLng}) {
          map.setZoom(14);
          map.setCenter(latLng)
        })
    }
    
    const connect = () => {
      const socket = new SockJS('https://i5d201.p.ssafy.io:8443/websocket');
      state.stompClient = Stomp.over(socket);
      state.stompClient.connect({}, function () {
        state.stompClient.subscribe(`/topic/marker/${state.conferneceNo}`, function (marker) {
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
      state.stompClient.send(`/app/marker/${state.conferneceNo}`, {}, JSON.stringify(position.toJSON(), null, 2));
    }


    function showMarker(marker) {
      state.markerList.push(marker)

      state.marker = new google.maps.Marker({
        position: state.markerList.slice(-1)[0],
        map: map,
      })
    }

    onMounted (() => {
      connect()
      initMap()
    })

    onUnmounted (() => {
      disconnect()
    })

    return { state, onMounted, map, connect, disconnect, showMarker, sendMarker, onUnmounted, onUpdated }
  },
}
</script>

<style>
#map {
  width: 100%;
  height: 50%;
}
</style>