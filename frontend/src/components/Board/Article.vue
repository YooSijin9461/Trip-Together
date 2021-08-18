<template>
  <div class="article-page">
    <div class="article-main mt-4">
      <h1 class="mb-4">{{ state.title }}</h1>
      <hr class="article-line mb-3">
      <div class="d-flex align-items-center">
        <div v-if="state.boardwriterImg">
          <img class="article-profile me-2" :src="'/upload/' + state.boardwriterImg">
        </div>
        <div v-else>
          <div v-if="state.boardwriterGender === 'm'">
            <img class="article-profile" :src="state.male" alt="">
          </div>
          <div v-else>
            <img class="article-profile" :src="state.female" alt="">
          </div>
        </div>
        <span class="article-userid" @click="clickProfile(state.boardwriterId)">{{ state.boardwriterId }}</span>
      </div>
      <hr class="article-line mt-3 mb-4">
      <div class="article-content">
        <span>{{ state.content }}</span>
        <img class="upload-img" :src="'/boards/upload/' + state.boardImg" alt="">
      </div>
      <hr>
      <div class="d-flex mb-2">
        <div v-if="!state.like">
          <div class="like" @click="clickLike">
            <span class="like-count">{{ state.likeCount }}</span> <i class="far fa-thumbs-up"></i> 좋아요
          </div>
        </div>
        <div v-else>
          <div class="liked" @click="clickLike">
            <span class="like-count">{{ state.likeCount }}</span> <i class="fas fa-thumbs-up"></i> 좋아요 취소
          </div>
        </div>
        <div v-if="!state.hate">
          <div class="hate" @click="clickHate">
            <span class="hate-count">{{ state.hateCount }}</span> <i class="far fa-thumbs-down"></i> 싫어요
          </div>
        </div>
        <div v-else>
          <div class="hated" @click="clickHate">
            <span class="hate-count">{{ state.hateCount }}</span> <i class="fas fa-thumbs-down"></i> 싫어요 취소
          </div>
        </div>
      </div>
      <div class="article-buttons">
        <el-button type="success" @click="clickToList">목록</el-button>
        <div v-if="state.boardwriterId === state.loginId" class="article-button">
          <el-button type="primary" @click="state.dialogVisible = true">수정</el-button>
          <el-button type="danger" @click="clickDelete(state.articleNo)">삭제</el-button>
        </div>
      </div>
      <h4>댓글</h4>
      <div class="d-flex">
        <el-input class="pe-2" placeholder="댓글을 입력해주세요." v-model="state.comment" @keyup.enter="createComment"></el-input>
        <el-button type="success" @click="createComment">작성</el-button>
      </div>
      <hr class="mb-1">
      <Comment
        v-for="(comment, idx) in state.commentList.slice().reverse()" :key="idx" :comment="comment"/>
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
              :rows="10"
              placeholder="내용을 입력해주세요."
              v-model="state.updateContent">
            </el-input>
          </div>
          <hr class="article-line my-0 mb-4">
          <input class="ms-4" @change="fileSelect()" id="boardimg" type="file" accept="image/*" />
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
import { reactive, computed, onUpdated, onMounted } from 'vue'
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
      boardwriterId: computed (() => store.getters['getBoarduserid']),
      title: computed (() => store.getters['getBoardtitle']),
      content: computed (() => store.getters['getBoardcontent']),
      boardImg: computed (() => store.getters['getBoardimg']),
      boardwriterImg: computed (() => store.getters['getBoarduserimg']),
      boardwriterGender: computed (() => store.getters['getBoardusergender']),
      updateTitle: store.getters['getBoardtitle'],
      updateContent: store.getters['getBoardcontent'],
      updateImg: store.getters['getBoardimg'],
      articleNo: computed (() => store.getters['getBoardno']),
      loginId: computed (() => store.getters['getUserid']),
      dialogVisible: false,
      comment: '',
      commentList: computed (() => store.getters['getCommentlist']),
      like: computed (() => store.getters['getLike']),
      hate: computed (() => store.getters['getHate']),
      likeCount: computed (() => store.getters['getLikecount']),
      hateCount: computed (() => store.getters['getHatecount']),
      male: require('@/assets/male.png'),
      female: require('@/assets/female.png'),
    })

    const fileSelect = () => {
      const boardimg = document.getElementById("boardimg")
      state.updateImg = boardimg.files[0]
    }
    const clickToList = () => {
      router.push({ name: 'ArticleList' })
    }
    const clickUpdate = (articleNo) => {
      // state.title = state.updateTitle
      // state.content = state.updateContent

      const formData = new FormData()
        formData.append('boardNo', articleNo)
        formData.append('boardTitle', state.updateTitle)
        formData.append('boardContent', state.updateContent)
        formData.append('file', state.updateImg)
        
        const params = new URLSearchParams();
        params.append('boardTitle', state.updateTitle)
        params.append('boardContent', state.updateContent)

      store.dispatch('articleUpdate', { boardNo: articleNo, formData: formData, params: params })
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
    const clickProfile = (userId) => {
      store.dispatch('profile', userId)
        .then(() => {
          router.push({ name: 'Profile', params: { userId: userId }})  
        })
    }
    const createComment = () => {
      if (state.comment) {
        store.dispatch('createComment', { 
          boardNo: state.articleNo, 
          comment: state.comment, 
          userId: state.loginId
        })
          .then(({ data }) => {
            ElMessage ({
              message: '댓글을 작성하였습니다.',
              type: 'success',
            });
            store.dispatch('commentList', data.boardNo)
              .then(() => {
                state.comment = ''
              })
          })
      } else {
        ElMessage.error("댓글을 입력하세요.")
      }
    }
    const clickLike = () => {
      store.dispatch('articleLike', { userId: state.loginId, boardNo: state.articleNo })
        .then(() => {
          store.dispatch('recommend', { userId: state.loginId, boardNo: state.articleNo })
        })
    }
    const clickHate = () => {
      store.dispatch('articleHate', { userId: state.loginId, boardNo: state.articleNo })
        .then(() => {
          store.dispatch('recommend', { userId: state.loginId, boardNo: state.articleNo })
        })
    }
    onMounted (() => {
      store.dispatch('recommend', { userId: state.loginId, boardNo: state.articleNo })
    })
    onUpdated (() => {
      store.dispatch('commentList', state.articleNo)
      store.dispatch('articleDetail', state.articleNo)
    })
    return { state, fileSelect, clickToList, clickDelete, clickUpdate, clickProfile, createComment, clickLike, clickHate }
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
.article-content {
  min-height: 30%;
  word-break: break-all;
}
.article-buttons {
  display: flex;
  justify-content: space-between;
  margin-bottom: 5%;
}
.article-update {
  min-width: 400px;
}
.article-userid:hover {
  cursor: pointer;
  color: green;
  font-weight: bold;
}
.like {
  padding: 2px;
  margin-right: 10px;
}
.like:hover {
  cursor: pointer;
  color: #409eff;
  font-weight: bold;
}
.hate {
  padding: 2px;
}
.hate:hover {
  cursor: pointer;
  color: #f56c6c;
  font-weight: bold;
}
.liked {
  padding: 2px;
  margin-right: 5px;
  color: #409eff;
  font-weight: bold;
}
.hated {
  padding: 2px;
  color: #f56c6c;
  font-weight: bold;
}
.liked:hover {
  cursor: pointer;
}
.hated:hover {
  cursor: pointer;
}
.like-count {
  color: #409eff;
  font-weight: bold;
}
.hate-count {
  color: #f56c6c;
  font-weight: bold;
}
.upload-img {
  height: 100%;
  width: 100%;
}
input[type=file]::file-selector-button {
  height: 40px;
  border: 1px solid #dcdfe6;
  padding: 0 .4em;
  border-radius: .2em;
  background-color: #fff;
}
input[type=file]::file-selector-button:hover {
  cursor: pointer;
  background-color: #e4ffe4;
  border: 2px solid lightgreen;
  font-weight: bold;
}
</style>