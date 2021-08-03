<template>
  <div>
    <Main/>
    <Sidebar
      @openSearchDialog="onOpenSearchDialog"/>
  </div>
  <div class="ms-5 ps-5">
    <hr class="mt-2">
  </div>
  <div class="ms-5 mt-3">
    <router-view
      @openConferenceDialog="onOpenConferenceDialog"
      @openConferenceCreateDialog="onOpenConferenceCreateDialog"/>
  </div>
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
import Aside from '@/components/Main/Aside.vue'
import Search from '@/components/Main/Search.vue'
import ConferenceDialog from '@/components/Conference/ConferenceDialog.vue'
import ConferenceCreate from '@/components/Conference/ConferenceCreate.vue'
import Sidebar from '@/components/Main/Sidebar.vue'
import { reactive } from 'vue'

export default {
  name: 'App',
  components: {
    Main,
    Aside,
    Search,
    ConferenceDialog,
    ConferenceCreate,
    Sidebar,
  },
  setup() {
    const state = reactive ({
      searchDialogOpen: false,
      conferenceCreateDialogOpen: false,
      conferenceDialogOpen: false,
    })

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

    return { state, onOpenSearchDialog, onCloseSearchDialog, onOpenConferenceDialog, onCloseConferenceDialog, onOpenConferenceCreateDialog, onCloseConferenceCreateDialog }
  }
}
</script>

<style>
</style>