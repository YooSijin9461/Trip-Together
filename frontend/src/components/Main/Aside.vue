<template>
  <div class="d-flex flex-column aside-bar">
    <div :class="{ active: state.home }" class="aside-menu">
      <i class="fas fa-home aside-icon my-2" @click="clickHome"></i>
      <p class="aside-text mt-0">홈</p>
    </div>
    <div :class="{ active: state.conference }" class="aside-menu">
      <i class="fas fa-video aside-icon my-2" @click="clickConference"></i>
      <p class="aside-text mt-0">방</p>
    </div>
    <div :class="{ active: state.board }" class="aside-menu">
      <i class="far fa-clipboard aside-icon2 my-2" @click="clickBoard"></i>
      <p class="aside-text mt-0">게시판</p>
    </div>
    <div class="aside-menu">
      <i class="fas fa-search aside-icon my-2" @click="clickSearch"></i>
      <p class="aside-text mt-0">검색</p>
    </div>
    <div>
      {{ sideMenu }}
    </div>
  </div>
</template>

<script>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'Aside',
  setup(props, { emit }) {
    const router = useRouter()
    const state = reactive ({
      home: false,
      conference: false,
      board: false,
    })
    const clickHome = () => {
      router.push({ name: 'Home' })
    }
    const clickConference = () => {
      router.push({ name: 'ConferenceList' })
    }
    const clickBoard = () => {
      router.push({ name: 'Board' })
    }
    const clickSearch = () => {
      emit('openSearchDialog')
    }
    return { state, clickHome, clickConference, clickSearch, clickBoard }
  },
}
</script>
<style>
.active {
  font-weight: bold;
  color: orange;
}
.aside-icon {
  font-size: 25px;
}
.aside-icon2 {
  font-size: 30px;
}
.aside-bar {
  width: 50px;
}
.aside-menu {
  text-align: center;
}
.aside-menu:hover {
  cursor: pointer;
  color: orange;
  font-size: 30px;
  font-weight: bold;
}
.aside-text {
  margin-top: 2px;
  font-size: 4px;
}
</style>