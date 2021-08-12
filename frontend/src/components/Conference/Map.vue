<template>
  <GMapMap
    :center="state.center"
    :zoom="12"
    map-type-id="terrain">
    <GMapMarker
      :position="state.marker.position"
      :clickable="true"
      :draggable="true"
      @click="state.center=state.marker.position"
      />
</GMapMap>
</template>

<script>
import { reactive } from 'vue'

export default {
  setup() {
    const state = reactive ({
      center: { lat: 37.564214, lng: 127.001699 },
      marker: {
        position: {
          lat: 37.564214,
          lng: 127.001699}
        },
    })
    return { state }
  },
}
</script>