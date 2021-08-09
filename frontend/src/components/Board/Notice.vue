<template>
  <div class="notice-page">
    <div class="notice-main mt-4">
      <h1 class="mb-4">{{ state.title }}</h1>
      <hr class="notice-line mt-3 mb-4">
      <span>{{ state.content }}</span>
      <div class="to-list">
        <el-button type="primary" @click="clickToList">목록</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  setup() {
    const store = useStore()
    const router = useRouter()
    
    const state = reactive ({
      title: computed (() => store.getters['getNoticetitle']),
      content: computed (() => store.getters['getNoticecontent'])
    })
    const clickToList = () => {
      router.push({ name: 'NoticeList' })
    }

    return { state, clickToList }
  },
}
</script>

<style>
.notice-line {
  color: gray;
}
.notice-page {
  display: flex;
  justify-content: center;
}
.notice-main {
  position: absolute;
  width: 80%;
  height: 85%;
}
.notice-update {
  max-width: 720px;
  width: 80%;
}
.to-list {
  position: absolute;
  bottom: 2%;
  left: 2%;
}
</style>