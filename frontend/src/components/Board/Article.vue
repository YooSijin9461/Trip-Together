<template>
  <div class="article-page">
    <div class="article-main mt-4">
      <h1 class="mb-4">{{ state.title }}</h1>
      <hr class="article-line mb-3">
      <div class="d-flex align-items-center">
        <img class="article-profile me-2" src="../../assets/selfie1.jpg">
        <span>{{ state.userId }}</span>
      </div>
      <hr class="article-line mt-3 mb-4">
      <span>{{ state.content }}</span>
      <hr>
      <h2>댓글</h2>
      <el-input placeholder="댓글을 입력해주세요." v-model="state.comment" @keyup.enter="createComment"></el-input>
      <hr>
      <Comment
        v-for="(comment, idx) in state.commentList" :key="idx" :comment="comment"/>
      <div class="to-list">
        <el-button type="primary" @click="clickToList">목록</el-button>
      </div>
      <div v-if="state.userId === state.loginId" class="article-button">
        <el-button @click="state.dialogVisible = true">수정</el-button>
        <el-button type="danger" @click="clickDelete(state.articleNo)">삭제</el-button>
      </div>
      <el-dialog
        custom-class="article-update"
        title="게시글 수정"
        v-model="state.dialogVisible">
          <div class="d-flex my-4 mx-5">
            <div class="col-2 d-flex align-items-center">
              <span>제목</span>
            </div>
            <el-input placeholder="제목을 입력해주세요." v-model="state.updateTitle"></el-input>
          </div>
          <hr class="article-line my-0">
          <div class="d-flex my-4 mx-5">
            <div class="col-2 d-flex align-items-center">
              <span>내용</span>
            </div>
            <el-input
              type="textarea"
              :rows="18"
              placeholder="내용을 입력해주세요."
              v-model="state.updateContent">
            </el-input>
          </div>
          <hr class="article-line my-0">
        <template #footer>
          <span class="dialog-footer">
            <el-button type="primary" @click="clickUpdate(state.articleNo)">수정</el-button>
            <el-button @click="state.dialogVisible = false">취소</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import Comment from '@/components/Board/Comment.vue'

export default {
  name: "Article",
  components: {
    Comment,
  },
  setup() {
    const store = useStore()
    const router = useRouter()

    const state = reactive ({
      userId: computed (() => store.getters['getBoarduserid']),
      title: computed (() => store.getters['getBoardtitle']),
      content: computed (() => store.getters['getBoardcontent']),
      updateTitle: store.getters['getBoardtitle'],
      updateContent: store.getters['getBoardcontent'],
      articleNo: computed (() => store.getters['getBoardno']),
      loginId: computed (() => store.getters['getUserid']),
      dialogVisible: false,
      comment: '',
      commentList: computed (() => store.getters['getCommentlist']),
    })
    const clickToList = () => {
      router.push({ name: 'ArticleList' })
    }
    const clickUpdate = (articleNo) => {
      state.title = state.updateTitle
      state.content = state.updateContent

      const updateData = {
        boardTitle: state.updateTitle,
        boardContent: state.updateContent
      }
      store.dispatch('articleUpdate', { boardNo: articleNo, data:updateData })
        .then(() => {
          state.dialogVisible = false
          ElMessage ({
            message: '게시글이 수정되었습니다.',
            type: 'success',
          })
          router.go(-1)
        })
    }
    const clickDelete = (articleNo) => {
      store.dispatch('articleDelete', articleNo)
        .then(() => {
          ElMessage ({
            message: '게시글이 삭제되었습니다.',
            type: 'success',
          })
          router.push({ name: 'ArticleList' })
        })
    }

    const createComment = () => {
      store.dispatch('createComment', { 
        boardNo: state.articleNo, 
        comment: state.comment, 
        userId: state.loginId
      })
        .then(({ data }) => {
          // state.comments.push(data)
          ElMessage ({
            message: '댓글을 작성하였습니다.',
            type: 'success',
          });
          store.dispatch('commentList', data.boardNo)
        })
    }
    return { state, clickToList, clickDelete, clickUpdate, createComment }
  },
}
</script>

<style>
.article-profile {
  border-radius: 100%;
  width: 25px;
  height: 25px;
}
.article-page {
  display: flex;
  justify-content: center;
}
.article-main {
  position: absolute;
  width: 80%;
  height: 85%;
}
.article-line {
  color: gray;
}
.article-update {
  max-width: 720px;
  width: 80%;
}
.to-list {
  position: absolute;
  bottom: 2%;
  left: 2%;
}
.article-button {
  position: absolute;
  bottom: 2%;
  right: 2%;
}
</style>