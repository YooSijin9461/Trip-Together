<template>
  <GMapMap
    :center="state.center"
    :zoom="12"
    map-type-id="terrain">
    <GMapMarker
      v-for="marker in state.marker"
      :key="marker"
      :position="marker.position"
      :clickable="true"
      :draggable="true"
      @click="checkPosition(marker.position)">
    </GMapMarker>
  </GMapMap>
</template>

<script>
import { reactive } from 'vue'

export default {
  setup() {
    const state = reactive ({
      markerPosition: null,
      center: { lat: 37.564214, lng: 127.001699 },
      marker: [
        {
          position: {
            lat: 37.564214,
            lng: 127.001699
          }
        }
      ],
    })
    const checkPosition = (markerPosition) => {
      state.markerPosition = markerPosition
      console.log(state.markerPosition)
    }
    console.log(state.marker.position)
    return { state, checkPosition }
  },
}
</script>