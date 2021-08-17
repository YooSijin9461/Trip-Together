<template>
<el-dialog custom-class="Logout" title="로그아웃" v-model="state.dialogVisible" @close="handleClose">
  <span>정말 로그아웃 하시겠습니까?</span>
  <template #footer>
    <span class="dialog-footer">
      <el-button @click="handleClose">취소</el-button>
      <el-button type="danger" @click="clickLogout">로그아웃</el-button>   
    </span>
  </template>
</el-dialog>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

export default {
  name: 'Login',
  props: {
    open: {
      type: Boolean,
      default: false,
    }
  },
  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px',
    })

    const clickLogout = () => {
      store.dispatch('logout')
      .then(() => {
          ElMessage.error('로그아웃 되었습니다.')
          emit('closeLogoutDialog')
          router.push({ name: 'Home' })
      })
    }
    const handleClose = () => {
      emit('closeLogoutDialog')
    }

    return { state, clickLogout, handleClose }
  },
}
</script>

<style>
.Logout {
  width: 400px !important;
}
</style>