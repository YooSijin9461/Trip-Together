<template>
  <div class="container mt-4">
    <h1 class="mb-4">{{ state.title }}</h1>
    <hr class="notice-line mt-3 mb-4">
    <span>{{ state.content }}</span>
  </div>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
export default {
  setup() {
    const store = useStore()
    
    const state = reactive ({
      title: computed (() => store.getters['getNoticetitle']),
      content: computed (() => store.getters['getNoticecontent'])
    })
    return { state }
  },
}
</script>

<style>
.notice-line {
  color: gray;
}
</style>