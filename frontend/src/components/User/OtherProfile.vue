<template>
  <div class="container mt-4">
    <div class="row d-flex align-items-center">
      <!-- 이미지 -->
      <div class="col-3">
        <el-upload action="#" list-type="picture-card" :auto-upload="false">
          <template #default>
            <i class="el-icon-plus"></i>
          </template>
          <template #file="{file}">
            <div>
              <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
              <span class="el-upload-list__item-actions">
                <span
                  class="el-upload-list__item-preview"
                  @click="handlePictureCardPreview(file)"
                >
                  <i class="el-icon-zoom-in"></i>
                </span>
                <span
                  v-if="!disabled"
                  class="el-upload-list__item-delete"
                  @click="handleDownload(file)"
                >
                  <i class="el-icon-download"></i>
                </span>
                <span
                  v-if="!disabled"
                  class="el-upload-list__item-delete"
                  @click="handleRemove(file)"
                >
                  <i class="el-icon-delete"></i>
                </span>
              </span>
            </div>
          </template>
        </el-upload>
      </div>
      <el-dialog v-model="dialogVisible">
        <img width="100%" :src="dialogImageUrl" alt="" />
      </el-dialog>
      <div class="col">
        <p>{{ state.userName }}</p>
        <p>{{ state.userId }}</p>
        <el-rate
          v-model="state.value"
          disabled
          show-score
          text-color="#ff9900"
          score-template="{value}">
        </el-rate>
      </div>
    </div>
  </div>
  <nav class="mt-5">
    <div class="nav nav-tabs justify-content-center" id="nav-tab" role="tablist">
      <button 
        class="nav-link active" 
        id="nav-conference-tab" 
        data-bs-toggle="tab" 
        data-bs-target="#nav-conference" 
        type="button" 
        role="tab" 
        aria-controls="nav-conference" 
        aria-selected="true">
        <i class="fas fa-video me-2"></i>방
      </button>
      <button 
        class="nav-link" 
        id="nav-article-tab" 
        data-bs-toggle="tab" 
        data-bs-target="#nav-article" 
        type="button" 
        role="tab" 
        aria-controls="nav-article" 
        aria-selected="false">
        <i class="far fa-clipboard me-2"></i>게시글
      </button>
    </div>
  </nav>
  <div class="tab-content" id="nav-tabContent">
    <div class="tab-pane fade show active" id="nav-conference" role="tabpanel" aria-labelledby="nav-conference-tab">
    </div>
    <div class="tab-pane fade" id="nav-article" role="tabpanel" aria-labelledby="nav-article-tab">
    </div>
  </div>
</template>

<script>
import { reactive, computed, onMounted, ref } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'Profile',
  components: {
  },
  setup() {
    const store = useStore()

    const state = reactive({
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      value: ref(3.7),
      userName: computed(() => store.getters['getOthername']),
      userId: computed(() => store.getters['getOtherid']),
    })
    // onMounted (() => {
    //   store.dispatch('otherProfile', state.token)
    // })
    const handleRemove = (file) => {
      console.log(file)
    }
    const handlePictureCardPreview = (file) => {
      state.dialogImageUrl = file.url
      state.dialogVisible = true
    }
    const handleDownload = (file) => {
      console.log(file)
    }
    return { state, handleRemove, handlePictureCardPreview, handleDownload }
  },
}
</script>

<style>
button[aria-selected="false"] {
  color: lightslategray;
}
.nav-tabs .nav-item.show .nav-link, .nav-tabs .nav-link.active {
  font-weight: bold;
  color: orange;
}
.nav-link:hover {
  font-weight: bold;
  color: orange;
}
</style>