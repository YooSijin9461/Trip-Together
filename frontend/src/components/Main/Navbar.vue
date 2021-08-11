<template>
  <img class="main-logo" src="../../assets/TTlogo.png" @click="clickLogo">
  <div class="nav-box d-flex justify-content-end align-items-center">
    <div class="nav-button">
      <span v-if="state.token">
        <el-dropdown>
          <span class="el-dropdown-link">{{ state.userName }}</span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="clickProfile">내 정보</el-dropdown-item>
              <el-dropdown-item @click="clickLogout">로그아웃</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </span>
      <span v-else>
        <el-button @click="clickSignup">회원가입</el-button>
        <el-button type="primary" @click="clickLogin">로그인</el-button>
      </span>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'Navbar',
  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()
    const state = reactive ({
      searchDialogOpen: false,
      token: computed(() => store.getters['getToken']),
      userName: computed(() => store.getters['getUsername']),
    })
    const clickLogo = () => {
      router.push({ name: 'Home'})
    }
    const clickLogin = () => {
      emit('openLoginDialog')
    }
    const clickSignup = () => {
      emit('openSignupDialog')
    }
    const clickLogout = () => {
      emit('openLogoutDialog')
    }
    const clickProfile = () => {
      router.push({ name: 'MyProfile' })
    }

    return { state, clickLogo, clickLogin, clickSignup, clickLogout, clickProfile }   
  },
}
</script>

<style>
.main-logo {
  position: absolute;
  left: 49%;
  top: 5px;
  width: 120px;
  height: 60px;
}
.menu-logo {
  display: inline-block;
}
.nav-box {
  height: 70px;
  padding-left: 78px;
  padding-right: 24px;
  border-bottom: 1px solid lightgreen;
}
.el-popper.is-light,.el-popper.is-light .el-popper__arrow::before {
  border:1px solid lightgreen !important;
} 
.el-dropdown-menu__item:focus,.el-dropdown-menu__item:not(.is-disabled):hover {
  background-color: #e4ffe4 !important;
  color: green !important;
}
.el-button--default:focus, .el-button--default:hover {
  color: green !important;
  border-color: lightgreen !important;
  background-color: #e4ffe4 !important;
}</style>