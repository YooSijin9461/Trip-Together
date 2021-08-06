<template>
  <div class="container">
    <div class="d-flex justify-content-end">
      <el-select type="text" v-model="state.category">
        <el-option label="전체보기" value=""></el-option>
        <el-option label="일반" value="normal"></el-option>
        <el-option label="가이드" value="guide"></el-option>
      </el-select>
    </div>
    <div v-if="state.category === ''">
      <ConferenceAll
        @openConferenceDialog="onOpenConferenceDialog"
        @openConferenceCreateDialog="onOpenConferenceCreateDialog"/>
    </div>
    <div v-else-if="state.category === 'normal'">
      <ConferenceNormal
        @openConferenceDialog="onOpenConferenceDialog"
        @openConferenceCreateDialog="onOpenConferenceCreateDialog"/>
    </div>
    <div v-if="state.category === 'guide'">
      <ConferenceGuide
        @openConferenceDialog="onOpenConferenceDialog"
        @openConferenceCreateDialog="onOpenConferenceCreateDialog"/>
    </div>
  </div>
  <ConferenceCreate
    :open="state.conferenceCreateDialogOpen"
    @closeConferenceCreateDialog="onCloseConferenceCreateDialog"/>
  <ConferenceDialog
    :open="state.conferenceDialogOpen"
    @closeConferenceDialog="onCloseConferenceDialog"/>
</template>

<script>
import { reactive } from 'vue'
import ConferenceAll from '@/components/Conference/ConferenceAll.vue'
import ConferenceNormal from '@/components/Conference/ConferenceNormal.vue'
import ConferenceGuide from '@/components/Conference/ConferenceGuide.vue'
import ConferenceCreate from '@/components/Conference/ConferenceCreate.vue'
import ConferenceDialog from '@/components/Conference/ConferenceDialog.vue'

export default ({
  name: 'ConferenceList',
  components: {
    ConferenceAll,
    ConferenceNormal,
    ConferenceGuide,
    ConferenceCreate,
    ConferenceDialog
  },
  setup() {
    const state = reactive ({
      category: '',
      conferenceDialogOpen: false,
      conferenceCreateDialogOpen: false,
    })
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

    return { state, onOpenConferenceDialog, onCloseConferenceDialog, onOpenConferenceCreateDialog, onCloseConferenceCreateDialog }
  },
})
</script>