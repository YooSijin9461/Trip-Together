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
        <div class="d-flex">
          <div class="me-5">
            <p class="my-3">ID : {{ state.userId }}</p>
            <p class="my-3">이름 : {{ state.userName }}</p>
            <p class="my-3">e-mail : {{ state.userEmail }}</p>
          </div>
          <div class="ms-5 ps-5">
            <p class="my-3">나이 : {{ state.userAge }}</p>
            <div v-if="state.userGender === 'm'">
              <p class="my-3">성별 : 남자</p>
            </div>
            <div v-else-if="state.userGender === 'f'">
              <p class="my-3">성별 : 여자</p>
            </div>
            <div v-if="state.userMBTI">
              <p class="my-3">MBTI : {{ state.userMBTI }}</p>
            </div>
            <div v-else>
              <p class="my-3">MBTI : 정보가 없습니다.</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <nav class="mt-5">
    <div class="nav nav-tabs justify-content-center" id="nav-tab" role="tablist">
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
      <button 
        class="nav-link active" 
        id="nav-conference-tab" 
        data-bs-toggle="tab" 
        data-bs-target="#nav-conference" 
        type="button" 
        role="tab" 
        aria-controls="nav-conference" 
        aria-selected="true">
        <i class="far fa-comment me-2"></i>댓글
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
      userName: computed(() => store.getters['getUsername']),
      userId: computed(() => store.getters['getUserid']),
      token: computed(() => store.getters['getToken']),
    })
    onMounted (() => {
      store.dispatch('profile', state.token)
        .then(({ data }) => {
          console.log(data)
        })
    })
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
  color: lightgreen;
}
.nav-link:hover {
  font-weight: bold;
  color: lightgreen;
}
.nav-tabs {
  border-bottom: 1px solid lightgreen;
}
.nav-tabs .nav-item.show .nav-link, .nav-tabs .nav-link.active {
    border-color: lightgreen lightgreen #fff;
}
</style>