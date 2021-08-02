<template>
  <div class="container mt-4">
    <h1 class="mb-4">{{ state.title }}</h1>
    <hr class="article-line mb-3">
    <div class="d-flex align-items-center">
      <img class="profile me-2" src="../../assets/selfie1.jpg">
      <span>{{ state.userId }}</span>
    </div>
    <hr class="article-line mt-3 mb-4">
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
      userId: computed (() => store.getters['getBoarduserid']),
      title: computed (() => store.getters['getBoardtitle']),
      content: computed (() => store.getters['getBoardcontent'])
    })
    return { state }
  },
}
</script>

<style>
.profile {
  border-radius: 100%;
  width: 25px;
  height: 25px;
}
.article-line {
  color: gray;
}
</style>