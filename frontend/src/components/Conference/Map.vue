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

      map.addListener('click', function({ latLng }) {
        // state.marker = new google.maps.Marker({
        //   position: latLng,
        //   map: map,
        // })
        sendMarker(latLng)
        state.marker = new google.maps.Marker({
          position: {
            lat: parseFloat(state.markerList.slice(-1).lat),
            lng: parseFloat(state.markerList.slice(-1).lng),
          },
          map: map,
        })
        // state.marker.addListener('dblclick', () => {
        //   state.marker.setMap(null)
        //   // for (var i = 0; i < state.markerList.length; i++) {
        //   //   if (state.markerList[i].position === state.marker.position) {
        //   //     state.markerList[i].state = 0
        //   //     sendMarker(state.marker.position, 0)
        //   //   }
        //   // }
        // })
        // state.marker.addListener('click', function() {
        //   map.setZoom(14);
        //   map.setCenter(state.marker.getPosition())
        // })
      });
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
      console.log(position)
      state.stompClient.send(`/app/marker/${state.conferneceNo}`, {}, JSON.stringify( position ));
    }

    // function showMarker(marker) {
    //   state.markerList.push(marker)
    //   console.log(state.markerList.slice(-1))
    //   state.marker = new google.maps.Marker({
    //     position: {
    //       lat: parseFloat(state.markerList.slice(-1).lat),
    //       lng: parseFloat(state.markerList.slice(-1).lng),
    //     },
    //     map: map,
    //   })
    //   console.log(state.marker)
    // }
    function showMarker(marker) {
      state.markerList.push(marker)
      // console.log(state.markerList.slice(-1))

      // state.marker = new google.maps.Marker({
      //   position: {
      //     lat: parseFloat(state.markerList.slice(-1).lat),
      //     lng: parseFloat(state.markerList.slice(-1).lng),
      //   },
      //   map: map,
      // })
      // state.marker.setMap(map)
    }

    // const shareMarker = () => {
      // for (var i = 0; i < state.markerList.length; i++) {
      //   if (state.markerList[i]) {
      //     console.log(state.markerList[i])
      //     state.marker = new google.maps.Marker({
      //       position: state.markerList[i],
      //       map: map,
      //     })
      //     state.marker.setMap(map)
      //   }
      // }
    // }
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