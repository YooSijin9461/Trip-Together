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
        @openConferenceDialog="onOpenConferenceDialog"/>
    </div>
    <div v-else-if="state.category === 'normal'">
      <ConferenceNormal
        @openConferenceDialog="onOpenConferenceDialog"/>
    </div>
    <div v-if="state.category === 'guide'">
      <ConferenceGuide
        @openConferenceDialog="onOpenConferenceDialog"/>
    </div>
  </div>
  <ConferenceDialog
    :open="state.conferenceDialogOpen"
    @closeConferenceDialog="onCloseConferenceDialog"/>
</template>

<script>
import { reactive } from 'vue'
import ConferenceAll from '@/components/Conference/ConferenceAll.vue'
import ConferenceNormal from '@/components/Conference/ConferenceNormal.vue'
import ConferenceGuide from '@/components/Conference/ConferenceGuide.vue'
import ConferenceDialog from '@/components/Conference/ConferenceDialog.vue'

export default ({
  name: 'ConferenceList',
  components: {
    ConferenceAll,
    ConferenceNormal,
    ConferenceGuide,
    ConferenceDialog
  },
  setup() {
    const state = reactive ({
      category: '',
      conferenceDialogOpen: false,
    })
    const onOpenConferenceDialog = () => {
      state.conferenceDialogOpen = true
    }
    const onCloseConferenceDialog = () => {
      state.conferenceDialogOpen = false
    }

    return { state, onOpenConferenceDialog, onCloseConferenceDialog }
  },
})
</script>