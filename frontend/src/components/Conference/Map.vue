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
    })
    const google = window.google
    var map
    function initMap() {
      map = new google.maps.Map(document.getElementById('map'), {
        center: { lat: 37.564214, lng: 127.001699 },
        zoom: 10,
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
    const connect = () => {
      const socket = new SockJS('https://i5d201.p.ssafy.io:8443/websocket');
      state.stompClient = Stomp.over(socket);
      console.log('stompClient =>' + state.stompClient)
      state.stompClient.connect({}, function (frame) {
        console.log('Connected: frame =>' + frame);
        state.stompClient.subscribe(`/topic/chat/${state.conferneceNo}`, function (chat) {
          // showChat(JSON.parse(chat.body));
          console.log(chat)
        });
      });
    }
    const disconnect = () => {
      if (state.stompClient !== null) {
        state.stompClient.disconnect();
      }
      console.log("Disconnected");
    }
    // const sendChat = () => {
    //   const date = new Date();
    //   const dateInfo = date.getHours() + ":" + ("0" + date.getMinutes()).slice(-2)
    //   state.stompClient.send(`/app/chat/${state.conferneceNo}`, {}, JSON.stringify({'name': state.name, 'message': state.message, 'date': dateInfo}));
    //   state.message = ''
    // }
    onMounted (() => {
      initMap()
      connect()
    })
    onUnmounted (() => {
      disconnect()
    })
    return { state, onMounted, map, connect, disconnect }
  },
}
</script>

<style>
#map {
  width: 100%;
  height: 40%;
}
</style>