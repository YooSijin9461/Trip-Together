<template>
  <Navbar
    @openLogoutDialog="onOpenLogoutDialog"
    @openLoginDialog="onOpenLoginDialog"
    @openSignupDialog="onOpenSignupDialog"/>
  <Logout
    :open="state.logoutDialogOpen"
    @closeLogoutDialog="onCloseLogoutDialog"/>
  <Login
    :open="state.loginDialogOpen"
    @closeLoginDialog="onCloseLoginDialog"/>
  <Signup
    :open="state.signupDialogOpen"
    @closeSignupDialog="onCloseSignupDialog"/>
</template>

<script>
import Navbar from '@/components/Main/Navbar.vue'
import Logout from '@/components/User/Logout.vue'
import Login from '@/components/User/Login.vue'
import Signup from '@/components/User/Signup.vue'
import { reactive } from 'vue'

export default {
  name: 'Main',
  components: {
    Navbar,
    Logout,
    Login,
    Signup,
  },
  setup() {
    const state = reactive ({
      logoutDialogOpen: false,
      loginDialogOpen: false,
      signupDialogOpen: false,
    })
    const onOpenLogoutDialog = () => {
      state.logoutDialogOpen = true
    }
    const onCloseLogoutDialog = () => {
      state.logoutDialogOpen = false
    }
    const onOpenLoginDialog = () => {
      state.loginDialogOpen = true
    }
    const onCloseLoginDialog = () => {
      state.loginDialogOpen = false
    }
    const onOpenSignupDialog = () => {
      state.signupDialogOpen = true
    }
    const onCloseSignupDialog = () => {
      state.signupDialogOpen = false
    }

    return { 
      state, 
      onOpenLogoutDialog, 
      onCloseLogoutDialog, 
      onOpenLoginDialog, 
      onCloseLoginDialog, 
      onOpenSignupDialog, 
      onCloseSignupDialog,
    }
  },
}
</script>