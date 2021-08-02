<template>
  <div class="container">
    <div class="d-flex justify-content-between">
      <el-select type="text" v-model="state.category">
        <el-option label="전체보기" value=""></el-option>
        <el-option label="일반" value="normal"></el-option>
        <el-option label="가이드" value="guide"></el-option>
      </el-select>
      <div v-if="state.token">
        <el-button @click="clickConferenceCreate">방 생성</el-button>
      </div>
    </div>
    <div class="row">
      <div class="col-4 g-4" v-for="conference in state.conferenceCatogories.slice().reverse()" :key="conference">
        <div class="d-flex justify-content-center">
          <div class="block">
            <img class="conference_thumbnail" src="../../assets/selfie1.jpg" @click="clickConference(conference.conferenceNo)">
            <div class="mt-2 d-flex align-items-center">
              <img class="profile" :src="state.circleUrl">
              <div>
                <span class="mt-2 mx-2">{{ conference.title }}</span><br>
                <span class="owner mx-2" @click="clickProfile">{{ conference.owner }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, reactive, computed, onUpdated } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
// import { register } from '@/assets/js/conferenceroom.js'

export default ({
  name: 'ConferenceList',
  setup(props, { emit }) {
    const router = useRouter()
    const store = useStore()
    // const socket = new WebSocket('wss://' + location.host + '/groupcall')

    const state = reactive ({
      circleUrl: require("@/assets/selfie.jpg"),
      token: computed(() => store.getters['getToken']),
      normal: computed(() => state.conferenceList.filter(function (conference) {
        return conference.conferenceCategory === 'normal'
      })),
      guide: computed(() => state.conferenceList.filter(function (conference) {
        return conference.conferenceCategory === 'guide'
      })),
      category: '',
      conferenceList: [],
      conferenceCatogories: [],
      // username: computed(() => store.getters['getUsername']),
      // participants: {},
    })


    const clickProfile = () => {
      router.push({ name: 'Profile'})  
    }
    const clickConferenceCreate = () => {
      emit('openConferenceCreateDialog')
    }
    const clickConference = (conferenceNo) => {
      store.dispatch('conferenceDetail', conferenceNo)
        .then(() => {
          // register(state.username, conferenceNo)
          // router.push({ name: 'Conference', params: { conferenceId: conferenceNo}})
          emit('openConferenceDialog')
        })
    }

    onMounted (() => {
      store.dispatch('conferenceList')
        .then((res) => {
          state.conferenceList = res.data
          state.conferenceCatogories = res.data
        })
    })
    onUpdated (() => {  
      if (state.category === 'normal') {
        state.conferenceCatogories = state.normal
      }
      else if (state.category === 'guide') {
        state.conferenceCatogories = state.guide
      } else {
        state.conferenceCatogories = state.conferenceList
      }
    })
    return { state, clickProfile, clickConferenceCreate, clickConference }
  },
})
</script>

<style>
img:hover {
  cursor: pointer;
}
.conference_thumbnail {
  border-radius: 20px;
  width: 300px;
  height: 200px;
}
.profile {
  border-radius: 100%;
  width: 50px;
  height: 50px;
}
.owner:hover {
  cursor: pointer;
  color: black;
  font-weight: bold;
}
</style>