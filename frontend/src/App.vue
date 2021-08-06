<template>
  <div>
    <Main/>
    <Sidebar
      @openSearchDialog="onOpenSearchDialog"
      @openLogoutDialog="onOpenLogoutDialog"/>
  </div>
  <div class="ms-5 ps-5">
    <hr class="mt-2">
  </div>
  <div class="router mt-4">
    <router-view
      @openConferenceDialog="onOpenConferenceDialog"
      @openConferenceCreateDialog="onOpenConferenceCreateDialog"/>
  </div>
  <Logout
    :open="state.logoutDialogOpen"
    @closeLogoutDialog="onCloseLogoutDialog"/>
  <Search
    :open="state.searchDialogOpen"
    @closeSearchDialog="onCloseSearchDialog"/>
  <ConferenceDialog
    :open="state.conferenceDialogOpen"
    @closeConferenceDialog="onCloseConferenceDialog"/>
  <ConferenceCreate
    :open="state.conferenceCreateDialogOpen"
    @closeConferenceCreateDialog="onCloseConferenceCreateDialog"/>
</template>

<script>
import Main from '@/views/Main.vue'
import Logout from '@/components/User/Logout.vue'
import Search from '@/components/Main/Search.vue'
import ConferenceDialog from '@/components/Conference/ConferenceDialog.vue'
import ConferenceCreate from '@/components/Conference/ConferenceCreate.vue'
import Sidebar from '@/components/Main/Sidebar.vue'
import { reactive } from 'vue'

export default {
  name: 'App',
  components: {
    Main,
    Logout,
    Search,
    ConferenceDialog,
    ConferenceCreate,
    Sidebar,
  },
  setup() {

    const state = reactive ({
      logoutDialogOpen: false,
      searchDialogOpen: false,
      conferenceCreateDialogOpen: false,
      conferenceDialogOpen: false,
    })

    const onOpenLogoutDialog = () => {
      state.logoutDialogOpen = true
    }
    const onCloseLogoutDialog = () => {
      state.logoutDialogOpen = false
    }
    const onOpenSearchDialog = () => {
      state.searchDialogOpen = true
    }
    const onCloseSearchDialog = () => {
      state.searchDialogOpen = false
    }
    const onOpenConferenceDialog = () => {
      state.conferenceDialogOpen = true
    }
    const onCloseConferenceDialog = () => {
      state.conferenceDialogOpen = false
    }
    const onOpenConferenceCreateDialog = () => {
      state.conferenceCreateDialogOpen = true
    }
    const onCloseConferenceCreateDialog = () => {
      state.conferenceCreateDialogOpen = false
    }

    return { state, onOpenLogoutDialog, onCloseLogoutDialog, onOpenSearchDialog, onCloseSearchDialog, onOpenConferenceDialog, onCloseConferenceDialog, onOpenConferenceCreateDialog, onCloseConferenceCreateDialog }
  }
}
</script>

<style>
.router {
  margin-left: 78px;
}
</style>