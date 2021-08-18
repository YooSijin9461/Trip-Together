<template>
<el-dialog custom-class="conferenceDialog" title="방 입장" v-model="state.dialogVisible" @close="handleClose">
  <div class="container">
    <div class="d-flex align-items-end">
      <h2 class="mb-0 me-auto">{{ state.title }}</h2>
      <span>{{ state.category }}</span>
    </div>
    <hr class="article-line mb-3">
    <div class="d-flex align-items-center">
      <div v-if="state.ownerImg">
        <img class="dialog-profile me-2" :src="'/upload/' + state.ownerImg">
      </div>
      <div v-else>
        <div v-if="state.ownerGender === 'm'">
          <img :src="state.male" alt="">
        </div>
        <div v-else>
          <img :src="state.female" alt="">
        </div>
      </div>
      <div v-if="state.userConference.length">
        <span>{{ state.ownerId }} 외 
          <span class="conference-participant">
            {{ state.userConference.length }}명
            <span class="user-tooltip">
              <span v-for="(user, index) in state.userConference" :key="index">
                <span v-if="index < state.userConference.length - 1">
                  <span>{{ user.userId }}, </span> 
                </span>
                <span v-else>
                  <span>{{ user.userId }}</span> 
                </span>
              </span>
            </span>
            <!-- <span class="tooltip"><span>{{ user.userId }}</span></span> -->
          </span>
        </span>
      </div>
      <div v-else>
        <span>{{ state.ownerId }}</span>
      </div>
    </div>
    <hr class="article-line mt-3 mb-3">
    <span>{{ state.description }}</span>
    <span v-if="state.password">
      <hr>
      <el-form :model="state" :rules="state.rules" ref="ConferenceDetailForm" :label-position="state.align">
        <el-form-item prop="inputPassword" label="비밀번호" :label-width="state.formLabelWidth">
          <el-input v-model="state.inputPassword" autocomplete="off" show-password></el-input>
        </el-form-item>
      </el-form>
    </span>
  </div>
  <template #footer>
    <span class="dialog-footer">
      <el-button type="success" @click="clickConferenceDialog()">입장하기</el-button>
    </span>
  </template>
</el-dialog>
</template>

<script>
import { reactive, computed, ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

export default {
  name: 'ConferenceDialog',
  props: {
    open: {
      type: Boolean,
      default: false,
    }
  },
  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()
    const conferenceDialogForm = ref(null)

    const state = reactive({
      token: computed(() => store.getters['getToken']),
      userId: computed(() => store.getters['getUserid']),
      owner: computed(() => store.getters['getConferenceowner']),
      ownerId: computed(() => store.getters['getConferenceownerid']),
      ownerImg: computed(() => store.getters['getConferenceownerimg']),
      ownerGender: computed(() => store.getters['getConferenceownergender']),
      male: require('@/assets/male.png'),
      female: require('@/assets/female.png'),
      title: computed(() => store.getters['getConferencetitle']),
      description: computed(() => store.getters['getConferencedescription']),
      category: computed(() => store.getters['getConferencecategory']),
      password: computed(() => store.getters['getConferencepassword']),
      inputPassword: '',
      limit: computed(() => store.getters['getConferencelimit']),
      conferenceNo: computed(() => store.getters['getConferenceno']),
      userConference: computed(() => store.getters['getUserconference']),
      onPassword: false,
      align: 'left',
      rules: {
        password: [
          { required: true, message: 'Please input Password', trigger: 'blur' }
        ],
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px',
    })
    const clickConferenceDialog = () => {
      if (state.token) {
        if (state.password) {
          if (state.password === state.inputPassword) {
            if (state.userConference.length + 1 < state.limit) {
              store.dispatch('conferenceEnter', { conferenceNo: state.conferenceNo, userId: state.userId })
              router.push({ name: 'Conference', params: { conferenceId: state.conferenceNo}})
              emit('closeConferenceDialog')
            } else {
              ElMessage.error('제한인원이 초과되었습니다.')
            }
          } else {
            ElMessage.error('비밀번호가 틀렸습니다.')
          }
        } else {
          if (state.userConference.length + 1 < state.limit) {
            store.dispatch('conferenceEnter', { conferenceNo: state.conferenceNo, userId: state.userId })
            router.push({ name: 'Conference', params: { conferenceId: state.conferenceNo}})
            emit('closeConferenceDialog')
          } else {
            ElMessage.error('제한인원이 초과되었습니다.')
          }
        }
      } else {
        ElMessage.error('로그인이 필요합니다.')
      }
    }
    const handleClose = () => {
      state.inputPassword = ''
      emit('closeConferenceDialog')
    }

    return { conferenceDialogForm, state, clickConferenceDialog, handleClose }
  },
}
</script>

<style>
.conferenceDialog {
  width: 400px !important;
}
.dialog-profile {
  border-radius: 100%;
  width: 20px;
  height: 20px;
}
.conference-participant .user-tooltip {
  position: absolute;
  z-index: 3;
  background: white;
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.3);
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 15px;
  font-weight: 400;
  opacity: 0;
  white-space: nowrap;
  pointer-events: none;
  transition: 0s;
}
.conference-participant:hover {
  color: green;
  font-weight: bold;
  cursor: pointer;
}
.conference-participant:hover .user-tooltip {
  opacity: 1;
  pointer-events: auto;
  transition: all 0.2s ease;
  top: 39%;
  left:30%;
  transform: translateY(-50%);
}
</style>