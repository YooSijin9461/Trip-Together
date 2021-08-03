<template>
  <div>
    <Main/>
    <!-- <Sidebar/> -->
  </div>
  <div class="ms-5 ps-5">
    <hr class="mt-2">
  </div>
  <div class="d-flex">
    <div class="aside">
      <Aside
        @openSearchDialog="onOpenSearchDialog"/>
    </div>
    <div class="flex-fill mx-5">
      <router-view
        @openConferenceCreateDialog="onOpenConferenceCreateDialog"/>
    </div>
  </div>
  <Search
    :open="state.searchDialogOpen"
    @closeSearchDialog="onCloseSearchDialog"/>
  <ConferenceCreate
    :open="state.conferenceCreateDialogOpen"
    @closeConferenceCreateDialog="onCloseConferenceCreateDialog"/>
</template>

<script>
import Main from '@/views/Main.vue'
import Aside from '@/components/Main/Aside.vue'
import Search from '@/components/Main/Search.vue'
import ConferenceCreate from '@/components/Conference/ConferenceCreate.vue'
// import Sidebar from '@/components/Main/Sidebar.vue'
import { reactive } from 'vue'

export default {
  name: 'App',
  components: {
    Main,
    Aside,
    Search,
    ConferenceCreate,
    // Sidebar,
    // SideMenu
  },
  setup() {
    const state = reactive ({
      searchDialogOpen: false,
      conferenceCreateDialogOpen: false,
    })

    const onOpenSearchDialog = () => {
      state.searchDialogOpen = true
    }
    const onCloseSearchDialog = () => {
      state.searchDialogOpen = false
    }
    const onOpenConferenceCreateDialog = () => {
      state.conferenceCreateDialogOpen = true
    }
    const onCloseConferenceCreateDialog = () => {
      state.conferenceCreateDialogOpen = false
    }

    return { state, onOpenSearchDialog, onCloseSearchDialog, onOpenConferenceCreateDialog, onCloseConferenceCreateDialog }
  }
}
</script>

<style>
.aside {
  margin-left: 17px;
}
</style>