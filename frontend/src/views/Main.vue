<template>
  <Navbar
    @openLogoutDialog="onOpenLogoutDialog"
    @openLoginDialog="onOpenLoginDialog"
    @openSignupDialog="onOpenSignupDialog"
    @openProfileUpdateDialog="onOpenProfileUpdateDialog"/>
  <Logout
    :open="state.logoutDialogOpen"
    @closeLogoutDialog="onCloseLogoutDialog"/>
  <Login
    :open="state.loginDialogOpen"
    @closeLoginDialog="onCloseLoginDialog"/>
  <Signup
    :open="state.signupDialogOpen"
    @closeSignupDialog="onCloseSignupDialog"/>
  <ProfileUpdate
    :open="state.profileUpdateDialogOpen"
    @closeProfileUpdateDialog="onCloseProfileUpdateDialog"/>
</template>

<script>
import Navbar from '@/components/Main/Navbar.vue'
import Logout from '@/components/User/Logout.vue'
import Login from '@/components/User/Login.vue'
import Signup from '@/components/User/Signup.vue'
import ProfileUpdate from '@/components/User/ProfileUpdate.vue'
import { reactive } from 'vue'

export default {
  name: 'Main',
  components: {
    Navbar,
    Logout,
    Login,
    Signup,
    ProfileUpdate,
  },
  setup() {
    const state = reactive ({
      logoutDialogOpen: false,
      loginDialogOpen: false,
      signupDialogOpen: false,
      profileUpdateDialogOpen: false,
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
    const onOpenProfileUpdateDialog = () => {
      state.profileUpdateDialogOpen = true
    }
    const onCloseProfileUpdateDialog = () => {
      state.profileUpdateDialogOpen = false
    }

    return { 
      state, 
      onOpenLogoutDialog, 
      onCloseLogoutDialog, 
      onOpenLoginDialog, 
      onCloseLoginDialog, 
      onOpenSignupDialog, 
      onCloseSignupDialog,
      onOpenProfileUpdateDialog,
      onCloseProfileUpdateDialog,
    }
  },
}
</script>