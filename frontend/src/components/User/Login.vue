<template>
<el-dialog custom-class="Login" title="로그인" v-model="state.dialogVisible" @close="handleClose">
  <el-form :model="state.form" :rules="state.rules" ref="loginForm" :label-position="state.form.align">
    <el-form-item prop="id" label="아이디" :label-width="state.formLabelWidth" >
      <el-input v-model="state.form.id" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item prop="password" label="비밀번호" :label-width="state.formLabelWidth">
      <el-input v-model="state.form.password" autocomplete="off" show-password></el-input>
    </el-form-item>
  </el-form>
  <template #footer>
    <span class="dialog-footer">
      <el-button type="primary" @click="clickLogin">로그인</el-button>
    </span>
  </template>
</el-dialog>
</template>

<script>
import { reactive, computed, ref } from 'vue'
import { useStore } from 'vuex'
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
    const loginForm = ref(null)

    const state = reactive({
      form: {
        id: '',
        password: '',
        align: 'left'
      },
      rules: {
        id: [
          { required: true, message: 'Please input ID', trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Please input password', trigger: 'blur' }
        ]
      },
      dialogVisible: computed(() => props.open),
      token: computed(() => store.getters['getToken']),
      formLabelWidth: '120px',
    })

    const clickLogin = () => {
      loginForm.value.validate((valid) => {
        if (valid) {
          store.dispatch('login', { id: state.form.id, password: state.form.password })
          .then(() => {
            if (state.token) {
              ElMessage ({
                message: 'Login Success !',
                type: 'success',
              });
              emit('closeLoginDialog')
            } else {
              ElMessage.error('Id or Password error !')
            }
          })
          .catch(function () {
            ElMessage.error('Id or Password error !')
          })
        } else {
          ElMessage.error('Login Failed !')
        }
      })
    }

    const handleClose = () => {
      state.form.id = ''
      state.form.password = ''
      emit('closeLoginDialog')
    }

    return { loginForm, state, clickLogin, handleClose }
  },
}
</script>

<style>
.Login {
  width: 400px !important;
}
</style>