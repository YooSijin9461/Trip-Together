<template>
  <div class="container">
    <h1 class="mt-5 mb-4">게시글</h1>
    <hr class="article-line my-0">
    <div class="d-flex my-4 mx-5">
      <div class="col-1 d-flex align-items-center">
        <span>제목</span>
      </div>
      <el-input placeholder="제목을 입력해주세요." v-model="state.input"></el-input>
    </div>
    <hr class="article-line my-0">
    <div class="d-flex my-4 mx-5">
      <div class="col-1 d-flex align-items-center">
        <span>별점</span>
      </div>
      <el-rate v-model="state.rate" allow-half/>
    </div>
    <div class="d-flex my-4 mx-5">
      <div class="col-1 d-flex align-items-center">
        <span>내용</span>
      </div>
      <el-input
        type="textarea"
        :rows="18"
        placeholder="내용을 입력해주세요."
        v-model="state.textarea">
      </el-input>
    </div>
    <hr class="article-line mt-0 mb-4">
    <div class="d-flex justify-content-end">
      <el-button class="me-1" @click="clickCancle">취소</el-button>
      <el-button type="primary" @click="clickOK">작성</el-button>
    </div>
  </div>
</template>

<script>
import { reactive, computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'

export default {
  setup() {
    const router = useRouter()
    const store = useStore()

    const state = reactive ({
      input: ref(''),
      textarea: ref(''),
      rate: ref(3.5),
      userid: computed(() => store.getters['getUserid']),
    })

    const clickCancle = () => {
      router.go(-1)
    }
    const clickOK = () => {
      store.dispatch('articleCreate', {
          userId: state.userid,
          boardTitle: state.input,
          boardContent: state.textarea,
          boardRate: state.rate })
        .then(({ data }) => {
          ElMessage ({
            message: 'Article Create Success !',
            type: 'success',
          });
          store.dispatch('articleDetail', data.boardNo)
            .then(() => {
              router.push({ name: 'Article', params: { articleId: data.boardNo }})
            })
        })
    }
    return { state, clickCancle, clickOK }
  },
}
</script>
