<template>
  <div class="container">
    <div class="row">
      <div class="col-4 g-4" v-for="conference in state.conferencePageList" :key="conference">
        <div class="d-flex justify-content-center">
          <div class="block">
            <img class="conference_thumbnail" :src="conference.thumbnailUrl" @click="clickConference(conference.conferenceNo)">
            <div class="mt-2 d-flex align-items-center">
              <div v-if="conference.ownerImg">
                <img class="profile" :src="'/upload/' + conference.ownerImg">
              </div>
              <div v-else>
                <div v-if="conference.ownerGender==='m'">
                  <img :src="state.male" alt="">
                </div>
                <div v-else>
                  <img :src="state.female" alt="">
                </div>
              </div>
              <div>
                <span class="mt-2 mx-2">{{ conference.title }}</span><br>
                <span class="owner mx-2" @click="clickProfile(conference.ownerId)">{{ conference.ownerId }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="container d-flex justify-content-end createBtn" v-if="state.token">
      <el-button class="me-5" type="success" @click="clickConferenceCreate">방 생성</el-button>
    </div>
  </div>
  <div class="pagination">
    <el-pagination
      :page-size="9"
      layout="prev, pager, next"
      :total="state.conferenceCount"
      @current-change="pageChange">
    </el-pagination>
  </div>
</template>

<script>
import { onMounted, reactive, computed, onUpdated } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'

export default ({
  name: 'ConferenceGuide',
  setup(props, { emit }) {
    const router = useRouter()
    const store = useStore()
    const state = reactive ({
      circleUrl: require("@/assets/selfie.jpg"),
      token: computed(() => store.getters['getToken']),
      guide: computed(() => state.conferenceList.filter(function (conference) {
        return conference.conferenceCategory === 'guide'
      })),
      category: 'guide',
      conferenceList: [],
      conferencePageList: [],
      conferenceCount: 0,
    })

    const clickProfile = (userId) => {
      store.dispatch('profile', userId)
        .then(() => {
          router.push({ name: 'Profile', params: { userId: userId }})  
        })
    }
    const clickConferenceCreate = () => {
      emit('openConferenceCreateDialog')
    }
    const clickConference = (conferenceNo) => {
      if (!state.token) {
        ElMessage.error('로그인이 필요합니다.')
      } else {
        store.dispatch('conferenceDetail', conferenceNo)
          .then(() => {
            emit('openConferenceDialog')
          })
      }
    }
    const pageChange = (val) => {
      const start = (val - 1) * 9
      state.conferencePageList = state.guide.slice(start, start + 9)
    }
    onMounted (() => {
      store.dispatch('conferenceList')
        .then(({ data }) => {
          state.conferenceList = data.content
          state.conferencePageList = state.guide.slice(0, 9)
          state.conferenceCount = state.guide.length
        })
    })
    onUpdated (() => {
      store.dispatch('conferenceList')
        .then(({ data }) => {
          state.conferenceList = data.content
          state.conferencePageList = state.guide.slice(0, 9)
          state.conferenceCount = state.guide.length
        })
    })
    return { state, clickProfile, clickConferenceCreate, clickConference, pageChange }
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
  color: green;
  font-weight: bold;
}
.pagination {
  position: fixed;
  bottom: 5rem;
  left: 45%;
  right: 55%;
}
.createBtn {
  position: absolute;
  bottom: 12rem;
}
</style>