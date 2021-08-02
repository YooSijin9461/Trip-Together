<template>
  <img class="main-logo ps-1 ms-4 me-auto" src="../../assets/logo.png" @click="clickLogo">
  <div class="nav-box d-flex align-items-center ps-2 mx-4 my-3">
    <div class="me-auto">
      <SideMenu
        @openSearchDialog="onOpenSearchDialog"/>
    </div>
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
  <Search
    :open="state.searchDialogOpen"
    @closeSearchDialog="onCloseSearchDialog"/>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import SideMenu from '@/components/Main/SideMenu.vue'
import Search from '@/components/Main/Search.vue'

export default {
  name: 'Navbar',
  components: {
    SideMenu,
    Search
  },
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
      router.push({ name: 'Profile' })
    }
    const onOpenSearchDialog = () => {
      state.searchDialogOpen = true
    }
    const onCloseSearchDialog = () => {
      state.searchDialogOpen = false
    }

    return { state, clickLogo, clickLogin, clickSignup, clickLogout, clickProfile, onOpenSearchDialog, onCloseSearchDialog }   
  },
}
</script>

<style>
.main-logo {
  position: absolute;
  left: 47.7%;
  width: 60px;
  height: 50px;
}
.menu-logo {
  display: inline-block;
}

.nav-box {
  height: 40px;
}
</style>